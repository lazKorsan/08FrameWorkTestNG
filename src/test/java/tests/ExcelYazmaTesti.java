package tests;

import org.testng.annotations.Test;
import utilities.YenidenKullanilabilirMetotlar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelYazmaTesti {

    @Test
    public void testSonucunuExceleYaz() {
        // 1. Masaüstü yolunu dinamik olarak alalım
        // Bu Method 10 numara 5 yıldız.
        // orjinal kod bu :     String desktopPath = System.getProperty("user.home") + "/Desktop/";
        //C:\Users\Hp\OneDrive\Desktop\video_kesmekliklik\kesilecek.mp4
        //C:\Users\Hp\OneDrive\Desktop\innerData\                 // BU PARÇA ALINMAZ       TestVerileri.xlsx
        String desktopPath = "C:\\Users\\Hp\\OneDrive\\Desktop\\innerData\\";

        String dosyaYolu = desktopPath + "TestVerileri.xlsx"; // Masaüstündeki dosyanın adı
        // masa ustunde TestVerileri.xlsx adında bir dosya oluşturulması gerekli

        // 2. Yazmak istediğimiz verileri hazırlayalım
        String testAdi = "Login Testi";
        String testSonucu = "PASSED";
        String zamanDamgasi = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 3. Reusable metodumuzu kullanarak Excel'e yazalım
        // Başlıkları yazalım (örneğin 0. satıra)
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 0, 0, "Test Adı");
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 0, 1, "Test Sonucu");
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 0, 2, "Tarih");

        // Test sonucunu yazalım (örneğin 1. satıra)
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 1, 0, testAdi);
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 1, 1, testSonucu);
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 1, 2, zamanDamgasi);

        // Başka bir test sonucunu 2. satıra yazalım
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 2, 0, "Arama Testi");
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 2, 1, "FAILED");
        YenidenKullanilabilirMetotlar.writeToExcel(dosyaYolu, 2, 2, zamanDamgasi);
    }
}