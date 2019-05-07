package io;

import dto.Account;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesIO {

    public List<Account> readAccountsFiles(String path) {
        List<Account> accountList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
           accountList = paths.map(Objects::toString).
                    filter(e -> !e.equals(path))
                    .map(this::readFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }


    public void createAccountsFiles(String path, List<Account> accounts) {
        clearAccountsFiles(path);
        AtomicInteger index = new AtomicInteger();
        String name = "/acc";
        accounts.forEach(a -> writeFile(path + name + index.getAndIncrement(), a));
    }

    private Account readFile(String path) {
        Account account = null;
        try (FileInputStream fileInputStream = new FileInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            account = (Account) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return account;
    }

    private void writeFile(String path, Account account) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    private void clearAccountsFiles(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
            new File(path).mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
