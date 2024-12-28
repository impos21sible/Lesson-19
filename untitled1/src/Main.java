import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            System.out.println("Введите путь к файлу/директории: ");
            String enteredPath = scanner.nextLine();
            Path path = Paths.get(enteredPath); // создаем переменную пути

            if (!Files.exists(path)) { // проверяем, не ошибся ли пользователь
                System.out.println("Введённый путь не существует.");
                continue; // продолжаем цикл, чтобы пользователь мог ввести новый путь
            }

            switch (command) {
                case "exit":
                    System.out.println("Выход.");
                    System.exit(0); // пользователь хочет найти выход, выход есть всегда
                    break;

                case "ls":
                    try {
                        String[] elements = path.toFile().list();
                        if (elements != null) {
                            for (String element : elements) {
                                System.out.println(element);
                            }
                        } else {
                            System.out.println("Путь не является директорией.");
                        }
                    } catch (Exception e) {
                        System.out.println("Произошла ошибка при запросе содержимого директории.");
                        e.printStackTrace();
                    }
                    break;

                case "mkdir":
                    try {
                        Files.createDirectory(path);
                        System.out.println("Директория создана: " + path);
                    } catch (IOException e) {
                        System.out.println("Произошла ошибка при создании директории.");
                        e.printStackTrace();
                    }
                    break;

                case "touch":
                    try {
                        Files.createFile(path);
                        System.out.println("Файл создан: " + path);
                    } catch (IOException e) {
                        System.out.println("Произошла ошибка при создании файла.");
                        e.printStackTrace();
                    }
                    break;

                case "rename":
                    System.out.println("Введите новое имя файла/директории: ");
                    String newName = scanner.nextLine();
                    Path newPath = path.getParent().resolve(newName); // создаем новый путь с новым именем

                    try {
                        Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Переименовано в: " + newPath);
                    } catch (IOException e) {
                        System.out.println("Произошла ошибка при переименовании файла/директории.");
                        e.printStackTrace();
                    }
                    break;

                case "rm_file":
                    try {
                        if (Files.isRegularFile(path)) {
                            Files.deleteIfExists(path);
                            System.out.println("Файл удален: " + path);
                        } else {
                            System.out.println("С помощью этой команды можно удалить только файл!");
                        }
                    } catch (IOException e) {
                        System.out.println("Произошла ошибка при удалении файла.");
                        e.printStackTrace();
                    }
                    break;

                default:
                    System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("ls - посмотреть содержимое директории.");
        System.out.println("mkdir - создать директорию.");
        System.out.println("touch - создать файл.");
        System.out.println("rename - переименовать директорию/файл.");
        System.out.println("rm_file - удалить файл.");
        System.out.println("exit - выход.");
    }
}