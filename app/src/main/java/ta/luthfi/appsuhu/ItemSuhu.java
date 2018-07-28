package ta.luthfi.appsuhu;

/**
 * Created by taufik on 01/07/18.
 */

public class ItemSuhu {
    String id_suhu, suhu, time;

    public ItemSuhu(String id_suhu, String suhu, String time) {
        this.id_suhu = id_suhu;
        this.suhu = suhu;
        this.time = time;
    }

    public String getId_suhu() {
        return id_suhu;
    }

    public String getSuhu() {
        return suhu;
    }

    public String getTime() {
        return time;
    }
}
