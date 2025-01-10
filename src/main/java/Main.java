import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        raceStart();
    }

    public static void raceStart(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<Car> allCompetitors = new ArrayList<>();
        for (int i=1; i<=3; i++){

            System.out.println("Введите название машины №" + i +"\"");
            String userInputBrand = scanner.next();//считывание строки до пробела

            String userInputSpeed = "";
            int speed = 0;
            while (!checkSpeedValue(speed) || !tryParse(userInputSpeed)) {
                System.out.println("Введите скорость машины №" + i + "\"");
                userInputSpeed = scanner.next();
                speed = tryParse(userInputSpeed)?Integer.parseInt(userInputSpeed): 0;
                if(!checkSpeedValue(speed) || !tryParse(userInputSpeed)) {
                    System.out.println("Неправильная скорость");
                }
            }
            allCompetitors.add(new Car(userInputBrand,speed));
        }
        scanner.close();
        Race winner = new Race();
        winner.calculateWhoIsWinner(allCompetitors);
    }
    public static boolean tryParse (String speed) {
        try {
            Integer.parseInt(speed);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean checkSpeedValue(int speed) {
        return (speed > 0 && speed <= 250) ? true : false;
    }
}

class Car {
    public String carBrand;
    public int speedValue;

    public Car (String carBrand, int speedValue) {
        this.carBrand = carBrand;
        this.speedValue = speedValue;
    }
}

class Race {
    public Car leader;
    public int completedWay;

    public Race (Car leader, int completedPath) {
        this.leader = leader;
        this.completedWay = completedPath;
    }
    public Race(){
    }

    public void calculateWhoIsWinner(ArrayList<Car> allCompetitors) {
        //Длительность гонки составляет 24 часа. Известна скорость. Необходимо найти пройденный путь.
        //Рассчитываем полученные результаты гонщиков и добавляем их в leadersList.
        ArrayList<Race> leadersList = new ArrayList<>();
        for (Car racerCar : allCompetitors) {
            leadersList.add(new Race(racerCar, racerCar.speedValue * 24));
        }
        //С помощью Iterator удаляем элементы удовлетворяющие условию way > element.completedWay.
        //Таким образом получаем список, который содержит победителя(-ей) гонки.
        for (Car racerCar : allCompetitors) {
            int way = racerCar.speedValue*24;
            Iterator<Race> iterator = leadersList.iterator();
            while (iterator.hasNext()) {
                Race element = iterator.next();
                if (way > element.completedWay) {
                    iterator.remove();
                }
            }
        }
        //Вывод результатов гонки.
        if (leadersList.size() == 1) {
            System.out.println("Самая быстрая машина: " + leadersList.getFirst().leader.carBrand);
        } else if (leadersList.size() >= 2) {
            System.out.println(leadersList.size() + " машины прибыли с одинаковым результатом:");
            for (Race leader : leadersList) {
                System.out.println(leader.leader.carBrand);
            }
        } else {
            System.out.println("Победителя определить не удалось");
        }
    }
}