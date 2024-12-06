package com.fpinjava.handlingerrors.exercise07_04;
import java.io.IOException;
import com.fpinjava.common.Function;

public class Toon {
    private final String firstName;
    private final String secondName;
    private final Result<String> email;

    public Toon(String frst, String scnd){
        firstName = frst;
        secondName = scnd;
        email = Result.failure(String.format("%s %s has no mail", firstName, secondName));
    }

    public Toon(String frst, String scnd, String email){
        firstName = frst;
        secondName = scnd;
        this.email = Result.success(email);
    }

    public Result<String> getEmail(){
        return email;
    }

    public String getEmailString(){
        return email.getOrElse("");
    }
    public static Result<String> getName(int num){
        switch (num) {
            case 1:
            return Result.success("Mickey");
            case 2:
            return Result.failure(new IOException("Input error"));
            case 3:
            return Result.success("Minnie");
            case 4:
            return Result.success("Goofy");
            default:
            return Result.failure(new IOException("Input error"));
        }
    }

    public static void main(String[] args) {
        Map<String, Toon> toons = new Map<String, Toon>()
                                        .put("Mickey", new Toon("Micky", "Mouse", "micky.mouse@disney.com"))
                                        .put("Minnie", new Toon("Minnie", "Mouse"))
                                        .put("Donald", new Toon("Donald", "Duck", "donald.duck@disney.com"));

        Function<String, Result<Toon>> funGetFlat = key -> toons.get(key);
        Function<String, Toon> mapGet = key -> toons.get(key).getOrElse(() -> new Toon(key, key));
        //Function<String, String> toonEmail = key -> Toon::getEmail().getOrElse(() -> new Toon(key, key));
        System.out.println(getName(1).toOption().map(mapGet).map(Toon::getEmailString));
        System.out.println(getName(2).flatMap(toons::get).flatMap(Toon::getEmail));
        System.out.println(getName(3).flatMap(toons::get).flatMap(Toon::getEmail));
        System.out.println(getName(4).flatMap(toons::get).flatMap(Toon::getEmail));
        System.out.println(getName(5).flatMap(toons::get).flatMap(Toon::getEmail));
    }
}
