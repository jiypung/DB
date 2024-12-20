package db.test.movie;

import io.moblie.conf.Conf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private static Movie setMovie(ResultSet rs) throws SQLException {
        String audienceId = rs.getString("audience_id");
        String audienceName = rs.getString("audience_name");
        int age = rs.getInt("age");
        String address = rs.getString("address");
        String membershipRank = rs.getString("membership_rank");

        return new Movie(audienceId, audienceName, age, address, membershipRank);
    }

    public static List<Movie> selectAll() {
        List<Movie> movieList = new ArrayList<>();
        String query = "SELECT * FROM audience";

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD);
             PreparedStatement psmtQuery = conn.prepareStatement(query);
             ResultSet rs = psmtQuery.executeQuery()) {

            while (rs.next()) {
                movieList.add(setMovie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public static Movie selectById(String audienceId) {
        String query = "SELECT * FROM audience WHERE audience_id = ?";
        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD);
             PreparedStatement psmtQuery = conn.prepareStatement(query)) {

            psmtQuery.setString(1, audienceId);
            try (ResultSet rs = psmtQuery.executeQuery()) {
                if (rs.next()) {
                    return setMovie(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int insert(Movie movie) {
        String query = "INSERT INTO audience (audience_id, audience_name, age, address, membership_rank) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD);
             PreparedStatement psmtUpdate = conn.prepareStatement(query)) {

            psmtUpdate.setString(1, movie.getAudienceId());
            psmtUpdate.setString(2, movie.getAudienceName());
            psmtUpdate.setInt(3, movie.getAge());
            psmtUpdate.setString(4, movie.getAddress());
            psmtUpdate.setString(5, movie.getMembershipRank());

            return psmtUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int update(Movie movie) {
        String query = "UPDATE audience SET audience_name = ?, age = ?, address = ?, membership_rank = ? WHERE audience_id = ?";
        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD);
             PreparedStatement psmtUpdate = conn.prepareStatement(query)) {

            psmtUpdate.setString(1, movie.getAudienceName());
            psmtUpdate.setInt(2, movie.getAge());
            psmtUpdate.setString(3, movie.getAddress());
            psmtUpdate.setString(4, movie.getMembershipRank());
            psmtUpdate.setString(5, movie.getAudienceId());

            return psmtUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int deleteById(String audienceId) {
        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            conn.setAutoCommit(false);

            String deletePayments = """
            DELETE FROM payment 
            WHERE ticket_id IN (
                SELECT ticket_id FROM ticket WHERE audience_id = ?
            )
        """;
            try (PreparedStatement psmtDeletePayments = conn.prepareStatement(deletePayments)) {
                psmtDeletePayments.setString(1, audienceId);
                psmtDeletePayments.executeUpdate();
            }

            String deleteTickets = "DELETE FROM ticket WHERE audience_id = ?";
            try (PreparedStatement psmtDeleteTickets = conn.prepareStatement(deleteTickets)) {
                psmtDeleteTickets.setString(1, audienceId);
                psmtDeleteTickets.executeUpdate();
            }

            String deleteAudience = "DELETE FROM audience WHERE audience_id = ?";
            try (PreparedStatement psmtDeleteAudience = conn.prepareStatement(deleteAudience)) {
                psmtDeleteAudience.setString(1, audienceId);
                int result = psmtDeleteAudience.executeUpdate();

                conn.commit();
                return result;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<String> selectAudienceDetails() {
        List<String> audienceDetails = new ArrayList<>();
        String query = """
            SELECT a.audience_id, a.audience_name, t.movie_name, t.screening_date, t.price, p.payment_method, p.payment_date
            FROM audience a
            LEFT JOIN ticket t ON a.audience_id = t.audience_id
            LEFT JOIN payment p ON t.ticket_id = p.ticket_id
            """;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD);
             PreparedStatement psmtQuery = conn.prepareStatement(query);
             ResultSet rs = psmtQuery.executeQuery()) {

            while (rs.next()) {
                String detail = String.format(
                        "고객 ID: %s, 이름: %s, 영화: %s, 상영 날짜: %s, 가격: %d, 결제 방식: %s, 결제 날짜: %s",
                        rs.getString("audience_id"),
                        rs.getString("audience_name"),
                        rs.getString("movie_name"),
                        rs.getDate("screening_date"),
                        rs.getInt("price"),
                        rs.getString("payment_method"),
                        rs.getDate("payment_date")
                );
                audienceDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audienceDetails;
    }
}
