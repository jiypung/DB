package db.test.movie;

import java.util.List;
import java.util.Scanner;

public class MovieMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. 고객 추가");
        while (true) {
            System.out.println("\n=== 영화 관리 시스템 ===");
            System.out.println("2. 고객 조회 (전체)");
            System.out.println("3. 고객 조회 (ID로)");
            System.out.println("4. 고객 정보 갱신");
            System.out.println("5. 고객 삭제");
            System.out.println("6. 고객 상세 정보 조회");
            System.out.println("7. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("고객 ID: ");
                    String audienceId = scanner.nextLine();

                    System.out.print("고객 이름: ");
                    String audienceName = scanner.nextLine();

                    System.out.print("고객 나이 (숫자 입력, 없으면 Enter): ");
                    String ageInput = scanner.nextLine();
                    Integer age = ageInput.isEmpty() ? null : Integer.valueOf(ageInput);

                    System.out.print("고객 주소 (없으면 Enter): ");
                    String address = scanner.nextLine();
                    address = address.isEmpty() ? null : address;

                    System.out.print("멤버십 등급 (silver/gold/vip, 없으면 Enter): ");
                    String membershipRank = scanner.nextLine();
                    membershipRank = membershipRank.isEmpty() ? "silver" : membershipRank;

                    if (!membershipRank.equals("silver") && !membershipRank.equals("gold") && !membershipRank.equals("vip")) {
                        System.out.println("잘못된 멤버십 등급입니다. 고객 추가를 취소합니다.");
                        break;
                    }

                    Movie newMovie = new Movie(
                            audienceId,
                            audienceName,
                            age,
                            address,
                            membershipRank
                    );
                    int result = MovieService.insert(newMovie);
                    System.out.println(result > 0 ? "고객 추가 성공!" : "고객 추가 실패!");
                }
                case 2 -> {
                    List<Movie> allMovies = MovieService.selectAll();
                    if (allMovies.isEmpty()) {
                        System.out.println("등록된 고객이 없습니다.");
                    } else {
                        System.out.println("\n=== 전체 고객 목록 ===");
                        for (Movie movie : allMovies) {
                            System.out.println(movie);
                        }
                    }
                }
                case 3 -> {
                    System.out.print("조회할 고객 ID: ");
                    String searchId = scanner.nextLine();
                    Movie movie = MovieService.selectById(searchId);
                    if (movie != null) {
                        System.out.println("고객 정보: " + movie);
                    } else {
                        System.out.println("해당 ID의 고객을 찾을 수 없습니다.");
                    }
                }
                case 4 -> {
                    System.out.print("갱신할 고객 ID: ");
                    String audienceId = scanner.nextLine();

                    System.out.print("새 고객 이름: ");
                    String audienceName = scanner.nextLine();

                    System.out.print("새 고객 나이 (숫자 입력, 없으면 Enter): ");
                    String ageInput = scanner.nextLine();
                    Integer age = ageInput.isEmpty() ? null : Integer.valueOf(ageInput);

                    System.out.print("새 고객 주소 (없으면 Enter): ");
                    String address = scanner.nextLine();
                    address = address.isEmpty() ? null : address;

                    System.out.print("새 멤버십 등급 (silver/gold/vip, 없으면 Enter): ");
                    String membershipRank = scanner.nextLine();
                    membershipRank = membershipRank.isEmpty() ? "silver" : membershipRank;

                    if (!membershipRank.equals("silver") && !membershipRank.equals("gold") && !membershipRank.equals("vip")) {
                        System.out.println("잘못된 멤버십 등급입니다. 고객 정보 갱신을 취소합니다.");
                        break;
                    }

                    Movie updatedMovie = new Movie(
                            audienceId,
                            audienceName,
                            age, 
                            address,
                            membershipRank
                    );
                    int result = MovieService.update(updatedMovie);
                    System.out.println(result > 0 ? "고객 정보 갱신 성공!" : "고객 정보 갱신 실패!");
                }
                case 5 -> {
                    System.out.print("삭제할 고객 ID: ");
                    String deleteId = scanner.nextLine();
                    int result = MovieService.deleteById(deleteId);
                    System.out.println(result > 0 ? "고객 삭제 성공!" : "고객 삭제 실패!");
                }
                case 6 -> {
                    List<String> audienceDetails = MovieService.selectAudienceDetails();
                    if (audienceDetails.isEmpty()) {
                        System.out.println("상세 정보가 없습니다.");
                    } else {
                        System.out.println("\n=== 고객 상세 정보 ===");
                        for (String detail : audienceDetails) {
                            System.out.println(detail);
                        }
                    }
                }
                case 7 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }
    }
}
