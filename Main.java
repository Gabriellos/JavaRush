package com.company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
CRUD 2
*/

public class Main {
    public static volatile List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) throws Exception {

        String key = args[0];
        String name = "";
        String sex = "";
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date birthday;
        int id = 0;

        switch (args[0]) {
            case ("-c"):
                for (int i = 1; i < args.length; i++) {
                    if (i % 3 == 1) name = args[i];
                    else if (i % 3 == 2) sex = args[i];
                    else if (i % 3 == 0) {
                        birthday = format.parse(args[i]);
                        synchronized (allPeople) {
                            createPerson(name, sex, birthday);
                        }
                    }
                }
                break;
            case ("-u"):
                for (int i = 1; i < args.length; i++) {
                    if (i % 4 == 1) id = Integer.parseInt(args[i]);
                    else if (i % 4 == 2) name = args[i];
                    else if (i % 4 == 3) sex = args[i];
                    else if (i % 4 == 0) {
                        birthday = format.parse(args[i]);
                        synchronized (allPeople) {
                            updatePerson(id, name, sex, birthday);
                        }
                    }
                }
                break;
            case ("-d"):
                for (int i = 1; i < args.length; i++) {
                    id = Integer.parseInt(args[i]);
                    synchronized (allPeople) {
                        deletePersonInfo(id);
                    }
                }
                break;
            case ("-i"):
                for (int i = 1; i < args.length; i++) {
                    id = Integer.parseInt(args[i]);
                    synchronized (allPeople) {
                        printPersonInfo(id);
                    }
                }
                break;
            default:
                synchronized (allPeople) {
                    throw new Exception("Invalid key");
                }
        }
    }
    public static void createPerson(String name, String sex, Date birthday) {
        if (sex.equals("м")) {
            Person male = Person.createMale(name,birthday);
            allPeople.add(male);
            System.out.println(allPeople.indexOf(male));
        }
        else if (sex.equals("ж")) {
            Person female = Person.createFemale(name,birthday);
            allPeople.add(female);
            System.out.println(allPeople.indexOf(female));
        } else System.out.println("Wrong character for sex");
    }

    public static void updatePerson(int id, String name, String sex, Date birthday) {
        Person person = allPeople.get(id);
        person.setName(name);
        person.setBirthDate(birthday);
        if (sex.equals("м")) person.setSex(Sex.MALE);
        else if (sex.equals("ж")) person.setSex(Sex.FEMALE);;
    }

    public static void deletePersonInfo(int id) {
        allPeople.get(id).setName(null);
        allPeople.get(id).setSex(null);
        allPeople.get(id).setBirthDate(null);
    }
    public static void printPersonInfo(int id) {
        char sex;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Person person = allPeople.get(id);
        if (person.getSex() == Sex.MALE) sex = 'м';
        else sex = 'ж';
        System.out.println(person.getName() + ' ' + sex + ' ' + formatter.format(person.getBirthDate()));
    }
}
