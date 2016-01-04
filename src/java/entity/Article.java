/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author remi
 */
@Entity
@XmlRootElement
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titre;
    // Par defaut les String deviennent des VARCHAR(255), on augmente cette taille.
    // Si vraiment on ne veut pas de limite, on mettra @Lob pour Large Object à la 
    // place. Cf tutorial sur JPA et le mapping
    @Column(length = 2000)
    private String content;
    // TIMESTAMP, contrairement à DATE, stocke l'heure en plus du jour/mois/année
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date time;

    @ElementCollection
    private List<String> images;

    /**
     * ***** Constructeur ******
     */
    public Article() {
    }

    public Article(String titre, String content) {
        this.content = content;
        this.titre = titre;
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Date date = new Date();
        this.time = new Date();
        this.images = new ArrayList();
    }

    /**
     * ***************
     */
    public void addImage(String nom) {
        this.images.add(nom);
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", titre=" + titre + ", content=" + content + ", time=" + time + '}';
    }

}
