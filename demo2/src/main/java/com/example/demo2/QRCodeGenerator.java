package com.example.demo2;// Java code to generate QR code
//Java package
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;
//zxing
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
//javax
import javax.imageio.ImageIO;
public class QRCodeGenerator {
    public static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    public static String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        System.out.println("Enter the name");
        String name = sc.nextLine();
        System.out.println("Enter the amount");
        String amount = sc.nextLine();
        try {
            generateQRCodeImage(String.format("Paying %s a amount of %s",name,amount), 350, 350, QR_CODE_IMAGE_PATH);
            //call the method displaying the qr code
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code" + e.getMessage());
        }
        try {
            File file = new File("MyQRCode.png");
            String decodedText = decodeQRCode(file);
            if(decodedText == null) {
                System.out.println("No QR Code found in the image");
            } else {
                System.out.println(decodedText);
                //pass decodedText into the method for displaying the page
            }
        } catch (IOException e) {
            System.out.println("Could not decode QR Code" + e.getMessage());
        }
    }
}