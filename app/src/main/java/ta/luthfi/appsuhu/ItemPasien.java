package ta.luthfi.appsuhu;

/**
 * Created by taufik on 01/07/18.
 */

public class ItemPasien {
    String id_pasien, nama, suhu, time;

    public ItemPasien(String id_pasien, String nama, String suhu, String time) {
        this.id_pasien = id_pasien;
        this.nama = nama;
        this.suhu = suhu;
        this.time = time;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public String getNama() {
        return nama;
    }

    public String getSuhu() {
        return suhu;
    }

    public String getTime() {
        return time;
    }
}
