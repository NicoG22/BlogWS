package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

@Path("/files")
public class JerseyFileUpload {

    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/Users/mbuffa/Desktop/";
    public static final String SERVER_UPLOAD_LOCATION_FOLDER = "D:\\Data\\Bibliotheques\\Documents\\Cours\\MBDS\\Web\\TP_vacances\\img\\";
    private static final String URL_SERVER_LOCATION_FOLDER ="../img/";
    /**
     * Upload a File
     * @param form
     * @return 
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(FormDataMultiPart form) {
        // Get all parts
        //List<BodyPart> parts = form.getBodyParts();        
        
        System.out.println("UPLOAD MULTIPART FORM");
        // GETTING THE FORM FIELDS
        String titre  = form.getField("titre").getValue();
        String contenu  = form.getField("contenu").getValue();
        
        System.out.println("Titre = " + titre);
        System.out.println("Contenu = " + contenu);
        
        // GETTING THE FILES
        List<FormDataBodyPart> files = form.getFields("file");

        String output = "No file to load";
                
        if(files != null) {
            System.out.println("J'ai récupéré et je sauvegarde les fichiers suivants : ");
            
            for (FormDataBodyPart filePart : files) {
                ContentDisposition headerOfFilePart = filePart.getContentDisposition();  
                String filePath = SERVER_UPLOAD_LOCATION_FOLDER + headerOfFilePart.getFileName();

                InputStream fileInputStream = filePart.getValueAs(InputStream.class);

                System.out.println("Fichier : " + filePath);

                // Get the inputStream for the file and save it
                saveFile(fileInputStream, filePath);
            }

            output = "Files saved to server location using FormDataMultiPart ! ";
        }
        

        return Response.status(200).entity(output).build();
    }

    // save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream, String serverLocation) {

        try {
            OutputStream outpuStream = null;
            int read = 0;
            byte[] bytes = new byte[1024];

            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();

            uploadedInputStream.close();
        } catch (IOException e) {

            System.out.println("Erreur dans la sauvegarde du fichier : " + serverLocation);
        }

    }

   
}
