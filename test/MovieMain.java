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

                        // 영화 및 결제 정보 출력
                        System.out.println("\n영화와 결제방법:");
                        List<Movie> movies = MovieService.getMoviesByAudienceName(audienceName);
                        if (movies != null) {
                            movies.forEach(System.out::println);
                        } else {
                            System.out.println("영화나 결제가 없습니다.");
                        }
                    } else {
                        System.out.println("\n해당 이름의 관객을 찾을 수 없습니다'" + audienceName + "'.");
                    }
                    break;

                case "2": // 추가
                    System.out.println("\n===== Add New Audience =====");
                    System.out.print("관객 ID를 입력해주세요: ");
                    String audienceId = scanner.nextLine();
                    System.out.print("관객 이름을 입력해주세요: ");
                    String newAudienceName = scanner.nextLine();
                    System.out.print("나이를 입력해주세요: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("주소를 입력해주세요: ");
                    String address = scanner.nextLine();
                    System.out.print("멤버쉽링크를 입력해주세요: ");
                    String membershipRank = scanner.nextLine();

                    int result = MovieService.addNewAudience(audienceId, newAudienceName, age, address, membershipRank);
                    if (result > 0) {
                        System.out.println("관객 등록이 성공적으로 이루어졌습니다");
                    } else {
                        System.out.println("관객 등록에 실패했습니다.");
                    }
                    break;

                case "3": // 종료
                    System.out.println("\n프로그램을 종료합니다.");
                    return;

                default: // 잘못된 입력
                    System.out.println("\n잘못된 입력입니다. 다시 입력해주시길 바랍니다.");
            }
        }
    }
}

