package db.test.movie.test;

public class Movie {
    private String movieName;
    private String screeningDate;
    private int price;
    private String paymentMethod;
    private String paymentDate;

    public Movie(String movieName, String screeningDate, int price, String paymentMethod, String paymentDate) {
        this.movieName = movieName;
        this.screeningDate = screeningDate;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getScreeningDate() {
        return screeningDate;
    }

    public int getPrice() {
        return price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieName='" + movieName + '\'' +
                ", screeningDate='" + screeningDate + '\'' +
                ", price=" + price +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}
