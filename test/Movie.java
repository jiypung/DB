package db.test.movie;

public class Movie {
    private String audienceId;
    private String audienceName;
    private int age;
    private String address;
    private String membershipRank;

    public Movie(String audienceId, String audienceName, int age, String address, String membershipRank) {
        this.audienceId = audienceId;
        this.audienceName = audienceName;
        this.age = age;
        this.address = address;
        this.membershipRank = membershipRank;
    }

    public String getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(String audienceId) {
        this.audienceId = audienceId;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audienceName) {
        this.audienceName = audienceName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembershipRank() {
        return membershipRank;
    }

    public void setMembershipRank(String membershipRank) {
        this.membershipRank = membershipRank;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "audienceId='" + audienceId + '\'' +
                ", audienceName='" + audienceName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", membershipRank='" + membershipRank + '\'' +
                '}';
    }
}
