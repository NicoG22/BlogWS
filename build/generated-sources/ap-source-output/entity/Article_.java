package entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-02T10:20:15")
@StaticMetamodel(Article.class)
public class Article_ { 

    public static volatile SingularAttribute<Article, Long> id;
    public static volatile SingularAttribute<Article, String> content;
    public static volatile SingularAttribute<Article, Date> time;
    public static volatile SingularAttribute<Article, String> titre;

}