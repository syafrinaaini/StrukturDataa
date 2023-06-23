import java.io.*;
import java.util.Scanner;

class Pelanggan implements Serializable {
    String nama;
    String alamat;
    String telepon;
    String nota;
    String tipe;

    public Pelanggan(String nama, String alamat, String telepon, String nota, String tipe) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.nota = nota;
        this.tipe = tipe;
    }
}

class LaundryStack {
    private final int MAX_SIZE = 100;
    private Pelanggan[] stackArray;
    private int top;

    public LaundryStack() {
        stackArray = new Pelanggan[MAX_SIZE];
        top = -1;
    }

    public void push(Pelanggan pelanggan) {
        if (top < MAX_SIZE - 1) {
            stackArray[++top] = pelanggan;
            System.out.println("Pelanggan berhasil ditambahkan.");
        } else {
            System.out.println("Stack sudah penuh, pelanggan tidak dapat ditambahkan.");
        }
    }

    public Pelanggan pop() {
        if (top >= 0) {
            Pelanggan pelanggan = stackArray[top--];
            System.out.println("Pelanggan " + pelanggan.nama + " dihapus dari stack.");
            return pelanggan;
        } else {
            System.out.println("Stack sudah kosong.");
            return null;
        }
    }

    public void displayStack() {
        System.out.println("Daftar pelanggan laundry:");
        for (int i = top; i >= 0; i--) {
            System.out.println("Nama Pelanggan: " + stackArray[i].nama);
            System.out.println("Alamat: " + stackArray[i].alamat);
            System.out.println("Telepon: " + stackArray[i].telepon);
            System.out.println("Nota: " + stackArray[i].nota);
            System.out.println("Tipe Laundry: " + stackArray[i].tipe);
            System.out.println();
        }
    }

    public void searchByName(String name) {
        boolean found = false;
        System.out.println("Hasil pencarian berdasarkan nama '" + name + "':");
        for (int i = top; i >= 0; i--) {
            if (stackArray[i].nama.equalsIgnoreCase(name)) {
                System.out.println("Nama Pelanggan: " + stackArray[i].nama);
                System.out.println("Alamat: " + stackArray[i].alamat);
                System.out.println("Telepon: " + stackArray[i].telepon);
                System.out.println("Nota: " + stackArray[i].nota);
                System.out.println("Tipe Laundry: " + stackArray[i].tipe);
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Pelanggan dengan nama '" + name + "' tidak ditemukan.");
        }
    }

    public void saveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data_pelanggan.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(stackArray);
            objectOut.close();
            fileOut.close();
            System.out.println("Data pelanggan berhasil disimpan.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            FileInputStream fileIn = new FileInputStream("data_pelanggan.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            stackArray = (Pelanggan[]) objectIn.readObject();
            top = stackArray.length - 1;
            objectIn.close();
            fileIn.close();
            System.out.println("Data pelanggan berhasil dimuat.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class LaundryApp {
    public static void main(String[] args) {
        LaundryStack stack = new LaundryStack();
        Scanner scanner = new Scanner(System.in);
        int pilihan;

        stack.loadData(); // Load data pelanggan dari file jika ada

        do {
            System.out.println("=== Aplikasi Data Pelanggan Laundry ===");
            System.out.println("1. Tambah Pelanggan");
            System.out.println("2. Hapus Pelanggan Teratas");
            System.out.println("3. Tampilkan Data Pelanggan");
            System.out.println("4. Cari Pelanggan berdasarkan Nama");
            System.out.println("5. Simpan Data Pelanggan");
            System.out.println("6. Keluar");
            System.out.print("Masukkan pilihan (1-6): ");
            pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.println("Masukkan nama pelanggan: ");
                    String nama = scanner.next();
                    System.out.println("Masukkan alamat pelanggan: ");
                    String alamat = scanner.next();
                    System.out.println("Masukkan nomor telepon pelanggan: ");
                    String telepon = scanner.next();
                    System.out.println("Masukkan nota pelanggan: ");
                    String nota = scanner.next();
                    System.out.println("Masukkan tipe laundry: ");
                    String tipe = scanner.next();

                    Pelanggan pelanggan = new Pelanggan(nama, alamat, telepon, nota, tipe);
                    stack.push(pelanggan);
                    break;
                case 2:
                    stack.pop();
                    break;
                case 3:
                    stack.displayStack();
                    break;
                case 4:
                    System.out.println("Masukkan nama pelanggan yang ingin dicari: ");
                    String searchName = scanner.next();
                    stack.searchByName(searchName);
                    break;
                case 5:
                    stack.saveData();
                    break;
                case 6:
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        } while (pilihan != 6);

        scanner.close();
    }
}
