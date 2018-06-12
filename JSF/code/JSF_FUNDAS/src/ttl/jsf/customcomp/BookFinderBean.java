package ttl.jsf.customcomp;


import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class BookFinderBean implements Serializable {
    private String title;
    private String isbn;
    private String author;
    private double price;

    private String result = "No Result";

    public BookFinderBean() {
    }

    public BookFinderBean(String title) {
        this.title = title;
    }

    public void doit() {
        //Do a find based on what's not null

        //We aim to please
        author = nullOrEmpty(author)? "Joe Smiley" : author;
        title = nullOrEmpty(title) ? "Over the Moon on a Dime" : title;
        isbn = nullOrEmpty(isbn) ? "2282-38383234-234" : isbn;
        price = 32.50;
    }

    public String getResult() {
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private boolean nullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    @Override
    public String toString() {
        return "BookFinderBean{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
