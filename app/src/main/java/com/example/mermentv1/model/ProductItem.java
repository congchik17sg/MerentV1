package com.example.mermentv1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductItem implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String productType;
    private String urlCenter;
    private String urlLeft;
    private String urlRight;
    private String urlSide;
    private int price;

    // Constructor
    public ProductItem(int id, String name, String description, String productType,
                       String urlCenter, String urlLeft, String urlRight, String urlSide, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productType = productType;
        this.urlCenter = urlCenter;
        this.urlLeft = urlLeft;
        this.urlRight = urlRight;
        this.urlSide = urlSide;
        this.price = price;
    }

    // Getter methods (and setter if needed)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProductType() {
        return productType;
    }

    public String getUrlCenter() {
        return urlCenter;
    }

    public String getUrlLeft() {
        return urlLeft;
    }

    public String getUrlRight() {
        return urlRight;
    }

    public String getUrlSide() {
        return urlSide;
    }

    public int getPrice() {
        return price;
    }

    // Parcelable implementation
    protected ProductItem(Parcel in) {
        id = in.readInt();  // Read from Parcel
        name = in.readString();  // Read from Parcel
        description = in.readString();  // Read from Parcel
        productType = in.readString();  // Read from Parcel
        urlCenter = in.readString();  // Read from Parcel
        urlLeft = in.readString();  // Read from Parcel
        urlRight = in.readString();  // Read from Parcel
        urlSide = in.readString();  // Read from Parcel
        price = in.readInt();  // Read from Parcel
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);  // Write to Parcel
        dest.writeString(name);  // Write to Parcel
        dest.writeString(description);  // Write to Parcel
        dest.writeString(productType);  // Write to Parcel
        dest.writeString(urlCenter);  // Write to Parcel
        dest.writeString(urlLeft);  // Write to Parcel
        dest.writeString(urlRight);  // Write to Parcel
        dest.writeString(urlSide);  // Write to Parcel
        dest.writeInt(price);  // Write to Parcel
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);  // Reconstruct the ProductItem from Parcel
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };
}
