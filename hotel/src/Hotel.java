import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

class HotelReservation {
    private static final String[] roomTypes = {"Standard Room", "Deluxe Room", "Suite"};
    private static final double[] roomPrices = {200000.0, 250000.0, 300000.0};  // Harga dalam Rupiah
    private static final double additionalPersonFee = 25000.0;  // Tambahan biaya per orang dalam Rupiah

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input informasi pemesanan
        System.out.print("Masukkan nama pemesan: ");
        String name = scanner.nextLine();

        System.out.print("Masukkan jumlah orang: ");
        int numPeople = scanner.nextInt();

        System.out.println("Tipe Kamar yang Tersedia:");
        displayRoomTypes();

        System.out.print("Pilih tipe kamar (1-3): ");
        int roomTypeIndex = scanner.nextInt() - 1;

        System.out.print("Masukkan tanggal mulai menginap (dd/MM/yyyy): ");
        String startDateStr = scanner.next();

        System.out.print("Masukkan tanggal selesai menginap (dd/MM/yyyy): ");
        String endDateStr = scanner.next();

        try {
            // Parse tanggal
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Hitung lama menginap
            int numNights = calculateNumNights(startDate, endDate);

            // Hitung harga total
            double totalPrice = calculateTotalPrice(roomTypeIndex, numPeople, numNights);

            // Tampilkan informasi pemesanan
            System.out.println("\nInformasi Pemesanan:");
            System.out.println("Nama Pemesan: " + name);
            System.out.println("Jumlah Orang: " + numPeople);
            System.out.println("Tipe Kamar: " + roomTypes[roomTypeIndex]);
            System.out.println("Tanggal Menginap: " + startDateStr + " hingga " + endDateStr);
            System.out.println("Lama Menginap: " + numNights + " malam");
            System.out.println("Harga Total: " + formatCurrency(totalPrice));

        } catch (ParseException e) {
            System.out.println("Format tanggal salah. Gunakan format dd/MM/yyyy.");
        } finally {
            scanner.close();
        }
    }

    private static void displayRoomTypes() {
        for (int i = 0; i < roomTypes.length; i++) {
            System.out.println((i + 1) + ". " + roomTypes[i] + " - " + formatCurrency(roomPrices[i]) + " per malam");
        }
    }

    private static int calculateNumNights(Date startDate, Date endDate) {
        // Hitung lama menginap
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    private static double calculateTotalPrice(int roomTypeIndex, int numPeople, int numNights) {
        double basePrice = roomPrices[roomTypeIndex];
        double totalPrice = basePrice + (numPeople > 2 ? (numPeople - 2) * additionalPersonFee : 0);
        return totalPrice * numNights;
    }

    private static String formatCurrency(double amount) {
        // Menggunakan objek NumberFormat untuk memformat jumlah ke dalam format mata uang Rupiah
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return currencyFormat.format(amount);
    }
}
