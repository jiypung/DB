package db.test.movie.test;

import db.test.movie.audience.Audience;
import java.util.List;
import java.util.Scanner;

public class MovieMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Audience Management =====");
            System.out.println("1. 이름으로 관객검색");
            System.out.println("2. 새 고객 추가");
            System.out.println("3. 종료하기");
            System.out.print("선택을 해주세요: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": // 검색
                    System.out.print("\n검색할 이름: ");
                    String audienceName = scanner.nextLine();

                    if (audienceName.equalsIgnoreCase("종료") || audienceName.equalsIgnoreCase("quit")) {
                        System.out.println("메인 선택페이지로 돌아갑니다...");
                        break;
                    }

                    Audience audience = MovieService.getAudienceByName(audienceName);
                    if (audience != null) {
                        System.out.println("\n관객 찾기:");
                        System.out.println(audience);

                        System.out.println("\n영화와 결제방법:");
                        List<Movie> movies = MovieService.getMoviesByAudienceName(audienceName);
                        if (movies != null) {
                            movies.forEach(System.out::println);
                        } else {
                            System.out.println("영화나 결제가 없습니다.");
                        }
                    } else {
                        System.out.println("\n해당 이름의 관객을 찾을 수 없습니다: '" + audienceName + "'.");
                    }
                    break;

                case "2": // 추가
                    System.out.println("\n===== Add New Audience =====");
                    String audienceId;
                    while (true) {
                        System.out.print("관객 ID를 입력해주세요: ");
                        audienceId = scanner.nextLine();
                        if (audienceId.isEmpty()) {
                            System.out.println("관객 ID는 필수 입력 항목입니다. 다시 입력해주세요.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("관객 이름을 입력해주세요: ");
                    String newAudienceName = scanner.nextLine();
                    if (newAudienceName.isEmpty()) {
                        System.out.println("관객 이름은 필수 입력 항목입니다. 다시 입력해주세요.");
                        continue;
                    }

                    Integer age = null;
                    while (true) {
                        System.out.print("나이를 입력해주세요 (빈칸 입력 시 생략됩니다): ");
                        String ageInput = scanner.nextLine();
                        if (ageInput.isEmpty()) {
                            System.out.println("빈칸으로 입력되었습니다. 나이는 NULL로 설정됩니다.");
                            break;
                        }
                        try {
                            age = Integer.parseInt(ageInput);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("유효하지 않은 나이입니다. 숫자를 입력해주세요.");
                        }
                    }

                    System.out.print("주소를 입력해주세요: ");
                    String address = scanner.nextLine();
                    if (address.isEmpty()) {
                        address = null;
                        System.out.println("주소는 비워둘 수 있습니다.");
                    }

                    String membershipRank;
                    while (true) {
                        System.out.print("멤버쉽 등급을 입력해주세요 (가능한 값: silver, gold, vip): ");
                        membershipRank = scanner.nextLine().toLowerCase();
                        if (membershipRank.isEmpty()) {
                            membershipRank = "silver";
                            System.out.println("빈칸으로 입력되었습니다. 멤버쉽 등급은 'silver'로 설정됩니다.");
                            break;
                        } else if (!membershipRank.equals("silver") && !membershipRank.equals("gold") && !membershipRank.equals("vip")) {
                            System.out.println("해당 멤버쉽 등급은 존재하지 않습니다. 다시 입력해주세요.");
                        } else {
                            break;
                        }
                    }

                    int result = MovieService.addNewAudience(audienceId, newAudienceName, (age != null ? age : 0), address, membershipRank);
                    if (result > 0) {
                        System.out.println("관객 등록이 성공적으로 이루어졌습니다.");
                    } else {
                        System.out.println("관객 등록에 실패했습니다.");
                    }
                    break;

                case "3":
                    System.out.println("\n프로그램을 종료합니다.");
                    return;

                default:
                    System.out.println("\n잘못된 입력입니다. 다시 입력해주시길 바랍니다.");
            }
        }
    }
}
