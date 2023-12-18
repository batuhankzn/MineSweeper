import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

    private int satirSayisi;
    private int sutunSayisi;
    private int mayinSayisi;
    private char[][] oyunTahtasi;
    private boolean[][] mayinKonumlari;
    private boolean oyunDevamEdiyor;

    public MineSweeper(int satirSayisi, int sutunSayisi) {
        this.satirSayisi = satirSayisi;
        this.sutunSayisi = sutunSayisi;
        this.mayinSayisi = satirSayisi * sutunSayisi / 4;
        this.oyunTahtasi = new char[satirSayisi][sutunSayisi];
        this.mayinKonumlari = new boolean[satirSayisi][sutunSayisi];
        this.oyunDevamEdiyor = true;

        // Oyun tahtasını başlangıç durumuna ayarla
        initOyunTahtasi();
        // Mayınları yerleştir
        yerlestirMayinlar();
    }

    // Oyun tahtasını başlangıç durumuna ayarla
    private void initOyunTahtasi() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                oyunTahtasi[i][j] = '-';
            }
        }
    }

    // Mayınları rastgele yerleştir
    private void yerlestirMayinlar() {
        Random random = new Random();
        int mayinSayaci = 0;

        while (mayinSayaci < mayinSayisi) {
            int satir = random.nextInt(satirSayisi);
            int sutun = random.nextInt(sutunSayisi);

            if (!mayinKonumlari[satir][sutun]) {
                mayinKonumlari[satir][sutun] = true;
                mayinSayaci++;
            }
        }
    }

    // Oyun tahtasını ekrana yazdır
    private void tahtayiYazdir() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(oyunTahtasi[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Kullanıcının girdiği noktayı kontrol et ve oyunu güncelle
    private void noktaSec(int satir, int sutun) {
        if (satir < 0 || satir >= satirSayisi || sutun < 0 || sutun >= sutunSayisi) {
            System.out.println("Geçersiz nokta! Lütfen tekrar girin.");
        } else {
            if (mayinKonumlari[satir][sutun]) {
                oyunDevamEdiyor = false;
                System.out.println("Mayına bastınız! Oyunu kaybettiniz.");
            } else {
                int etrafindakiMayinSayisi = etrafindakiMayinSayisi(satir, sutun);
                oyunTahtasi[satir][sutun] = (char) (etrafindakiMayinSayisi + '0');

                if (etrafindakiMayinSayisi == 0) {
                    // Etrafında mayın yoksa, etrafındaki noktalara da bak
                    acilmamisNoktalariAc(satir, sutun);
                }

                tahtayiYazdir();

                // Oyunu kazandı mı kontrol et
                if (kazandiMi()) {
                    oyunDevamEdiyor = false;
                    System.out.println("Tebrikler! Oyunu kazandınız.");
                }
            }
        }
    }

    // Seçilen noktanın etrafındaki mayın sayısını döndür
    private int etrafindakiMayinSayisi(int satir, int sutun) {
        int sayac = 0;
        for (int i = satir - 1; i <= satir + 1; i++) {
            for (int j = sutun - 1; j <= sutun + 1; j++) {
                if (i >= 0 && i < satirSayisi && j >= 0 && j < sutunSayisi && mayinKonumlari[i][j]) {
                    sayac++;
                }
            }
        }
        return sayac;
    }

    // Oyunu kazandı mı kontrol et
    private boolean kazandiMi() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if (oyunTahtasi[i][j] == '-' && !mayinKonumlari[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Açılmamış noktalara bak ve aç
    private void acilmamisNoktalariAc(int satir, int sutun) {
        for (int i = satir - 1; i <= satir + 1; i++) {
            for (int j = sutun - 1; j <= sutun + 1; j++) {
                if (i >= 0 && i < satirSayisi && j >= 0 && j < sutunSayisi && oyunTahtasi[i][j] == '-') {
                    int etrafindakiMayinSayisi = etrafindakiMayinSayisi(i, j);
                    oyunTahtasi[i][j] = (char) (etrafindakiMayinSayisi + '0');

                    if (etrafindakiMayinSayisi == 0) {
                        acilmamisNoktalariAc(i, j);
                    }
                }
            }
        }
    }

    // Oyunu başlat
    public void oyunuBaslat() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Mayın Tarlası Oyunu Başladı!");
        tahtayiYazdir();

        while (oyunDevamEdiyor) {
            System.out.print("Satır girin: ");
            int satir = scanner.nextInt();

            System.out.print("Sütun girin: ");
            int sutun = scanner.nextInt();

            noktaSec(satir, sutun);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Satır sayısını girin: ");
        int satirSayisi = scanner.nextInt();

        System.out.print("Sütun sayısını girin: ");
        int sutunSayisi = scanner.nextInt();

        MineSweeper mayinTarlası = new MineSweeper(satirSayisi, sutunSayisi);
        mayinTarlası.oyunuBaslat();

        scanner.close();
    }
}
