jQuery(document).ready(function($){
    // Ce code est appelée quand la page est chargée ou reloadée
    // On charge les 5 derniers articles (ou moins) depuis le web service
    
    $("#map").hide();
    
    // On cache le bouton de sauvegarde des modifications pour chaque article
    $("#update-article").hide();

    // URL du WS et fonction de callback en cas de succ-s
    $.get("/BlogWS2015/resources/article/0/5",function(data){
        
        var i = 0;
        // no article ?
        if(data !== null) {
            // The json returned by the WebService looks like this: (use JS debug console
            // in your browser !
            // [
            //    {"content":"Hello",
            //      "id":1,
            //      "time":"2015-10-22T09:21:31.696+02:00",
            //      "titre":"Hello"},
            //      ......
            // ]        // CAS 2 : plusieurs articles
            $(data).each(function(){
                i++
                $("#list-article").prepend(renderItem(this.id, this.titre, this.content, this.time));
            });
        
            // Si on est venu ici après une suppression on supprime le lien
            // "load more"
            if(removeLoadMore()){
                $("#loadmore").remove();
                console.log("DELETE");
            }else
            {
                // Si on vient ici après la publication d'un nouvel article on
                // fait apparitre le bouton "load more"
                console.log("INSERT");
                $("<div id='loadmore'><a href='#' id='load' ><i class='fa fa-plus'></i></a></div>").insertAfter("#list-article");
            }
        }
    },"json");
    
    
    // Si on clique sur le bouton "publier". L'utilisation de live() permet
    // de binder des events sur des éléments qui n'existent peut être pas encore
    $("#write").on('click',function(){
        // On récupère le contenu du formulaire en JSON
        var data = $("#myForm").serializeArray();
        // On fait un POST sur le web service d'insertion
        $.post("/BlogWS2015/resources/article",data,function(d){
            $("#myForm").each(function(){
                this.reset();
            });
            // On ajoute l'article dans la page
            $.get(d,function(data){
                $("#list-article").prepend(renderItem(data.id, data.titre, data.content, data.time));  
            });
        });
       
       // Il y a au moins un article, on supprime le message de bienvenue
        $("#welcome").remove();
       
        if(removeLoadMore()){
            $("#loadmore").remove();
        }

    });


    // Clic sur le bouton delete pour supprimer un article
    $(".delete").on("click",function(){

        var id = $(this).attr("href");
        console.log(id);

        $.ajax(id,
        {
            type:"DELETE",
            success: function(d){
                $("#article-"+d).slideUp('slow',function(){
                    $(this).remove()
                    });
            }
        });
        
        if(removeLoadMore()){
            $("#loadmore").remove();
        }
        
        return false;

    });

    // Click sur un titre d'article
    $(".title").on("click",function(){
        var url = $(this).attr("href");

        $(document).scrollTop($(document).height());

        $.get(url,function(data){

            $("#formupdate-article #titre").val(data.titre);
            $("#formupdate-article #content").val(data.content);
            $("#formupdate-article #id").val(data.id);

            $("#write-article").hide();
            $("#update-article").show();
        });
        
        if(removeLoadMore()){
            $("#loadmore").remove();
        }

        return false;
    });

    // Clic sur le bouton update pour modifier un article
    $("#update").on("click",function(){

        var data = $("#formupdate-article").serializeArray();
        console.log(data);
        
        $.ajax({
            url: "/BlogWS2015/resources/article",
            type:"PUT",
            data: data,
            success: function(d){
                $("#formupdate-article").each(function(){
                    this.reset();
                });

                updateRenderedItem(d.id, d.titre, d.content, d.time)

                $("#update-article").hide();
                $("#write-article").show();

            }

        });

        if(removeLoadMore()){
            $("#loadmore").remove();
        }

    });

    // clic sur le lien "load more"
    $("#load").on("click",function(){
        $("#loadmore").remove();
        var count = $("#list-article").children().length;
        var limit = count+5;

        $.get("/BlogWS2015/resources/article/"+count+"/"+limit,function(data){
          

            $(data).each(function(){
               
                $("#list-article").append(renderItem(this.id, this.titre, this.content, this.time));
            });
            if(removeLoadMore()){
                $("#loadmore").remove();
            }else{
                $("<div id='loadmore'><a href='#' id='load' ><i class='fa fa-plus'></i></a></div>").insertAfter("#list-article");
            }
        },"json");
    });
    

    function removeLoadMore()
    {
        $.get("/BlogWS2015/resources/article/count",function(data){
            var i = $("#list-article").children().length;
            console.log("dans la bd : "+data+" | sur le site : "+i);
            if(data == i){
                return true;
            }else
                return false;
        });
    }

    // creation et ajout d'un article dans la page
    function renderItem(id, titre, content, date)
    {
        var myDate = new Date( date );
        var strDate = "";
        strDate += myDate.getUTCDate()+"/"+myDate.getMonth()+"/"+myDate.getFullYear();
        strDate += " à "+myDate.getHours()+":"+myDate.getMinutes();
        return "<div class='article' id='article-"+id+"'>\
                <div class='postmeta'>\n\
                    <p class='alignleft'>Article publi&eacute; le "+strDate+"</p>\n\
                    <h2>"+titre+"</h2></a>\
                    <p class='content'>"+content+"</p>\
                    <p class='alignright'>\n\
                        <a class='button blue delete' href='/BlogWS2015/resources/article/"+id+"'>Supprimer</a>\n\
                        <a href='/BlogWS2015/resources/article/"+id+"' class='button blue title'>Modifier</a>\
                    </p></div>\n\
                    <div class='clearfix'></div>\
                </div>";
    }

    function updateRenderedItem(id, titre, content)
    {
        console.log(id);

        $("#article-"+id+" h2").html(titre);
        $("#article-"+id+" .title").attr("rel","/BlogWS2015/resources/article/"+id);
        $("#article-"+id+" .content").html(content);

        $("#article-"+id).css("background-color","#E3F6CE");
        window.setTimeout(function() {  
            $("#article-"+id).css("background-color","white");
        }, 1000); 

    }

});