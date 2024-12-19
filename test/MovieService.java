package db.test.movie.test;

import db.test.conf.Conf;
import db.test.movie.audience.Audience;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public static Audience getAudienceByName(String audienceName) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM audience WHERE audience_name = ?";
            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, audienceName);
            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                return new Audience(
                        rs.getString("audience_id"),
                        rs.getString("audience_name"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getString("membership_rank")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException ignored) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    public static int addNewAudience(String audienceId, String audienceName, int age, String address, String membershipRank) {
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query =
                    "INSERT INTO audience (audience_id, audience_name, age, address, membership_rank) " +
                            "VALUES (?, ?, ?, ?, ?)";
            psmtUpdate = conn.prepareStatement(query);
            psmtUpdate.setString(1, audienceId);
            psmtUpdate.setString(2, audienceName);
            psmtUpdate.setInt(3, age);
            psmtUpdate.setString(4, address);
            psmtUpdate.setString(5, membershipRank);

            return psmtUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (psmtUpdate != null) {
                try {
                    psmtUpdate.close();
                } catch (SQLException ignored) {}
            }


        }
    }
    public static List<Movie> getMoviesByAudienceName(String audienceName) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        List<Movie> movieList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query =
                    "SELECT t.movie_name, t.screening_date, t.price, " +
                            "       p.payment_method, p.payment_date " +
                            "FROM audience a " +
                            "LEFT JOIN ticket t ON a.audience_id = t.audience_id " +
                            "LEFT JOIN payment p ON t.ticket_id = p.ticket_id " +
                            "WHERE a.audience_name = ?";
            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, audienceName);
            rs = psmtQuery.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getString("movie_name"),
                        rs.getString("screening_date"),
                        rs.getInt("price"),
                        rs.getString("payment_method"),
                        rs.getString("payment_date")
                );
                movieList.add(movie);
            }
            return movieList.isEmpty() ? null : movieList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException ignored) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {}
            }
        }
    }
}
