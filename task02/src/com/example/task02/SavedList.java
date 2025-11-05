package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final List<E> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SavedList(File file) {
        this.file = file;
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List<?>) {
                    list.addAll((List<E>) obj);
                }
            } catch (IOException | ClassNotFoundException e) {
                // при ошибке чтения считаем, что список пуст
                System.err.println("Не удалось загрузить данные из файла: " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении списка в файл", e);
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public E set(int index, E element) {
        E old = list.set(index, element);
        saveToFile();
        return old;
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        saveToFile();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        saveToFile();
        return removed;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
