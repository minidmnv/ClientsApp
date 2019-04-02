package pl.mnicinski.ClientsApp.domain.client.address;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

import static pl.mnicinski.ClientsApp.domain.validation.CommonObjectValidator.isNull;
import static pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator.longerThen;
import static pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator.notNull;

@Entity
public class Address {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private final UUID uuid;
    private final String street;
    private final String houseNumber;
    private final String flatNumber;

    /**
     * Default constructor
     */
    private Address() {
        this.uuid = null;
        this.street = null;
        this.houseNumber = null;
        this.flatNumber = null;
    }

    private Address(Builder builder) {
        uuid = builder.uuid;
        street = builder.street;
        houseNumber = builder.houseNumber;
        flatNumber = builder.flatNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(uuid, address.uuid) &&
                street.equals(address.street) &&
                houseNumber.equals(address.houseNumber) &&
                flatNumber.equals(address.flatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, street, houseNumber, flatNumber);
    }

    public boolean validateNew() {
        isNull().validate(uuid).throwIfInvalid("uuid for new values");
        validate();

        return true;
    }

    public boolean validate() {
        notNull()
                .and(longerThen(2))
                .validate(street).throwIfInvalid("street");
        notNull()
                .validate(houseNumber).throwIfInvalid("houseNumber");
        notNull()
                .validate(flatNumber).throwIfInvalid("flatNumber");

        return true;
    }

    public static final class Builder {
        private UUID uuid;
        private String street;
        private String houseNumber;
        private String flatNumber;

        public Builder() {
        }

        public Builder(Address copy) {
            this.uuid = copy.uuid;
            this.street = copy.street;
            this.houseNumber = copy.houseNumber;
            this.flatNumber = copy.flatNumber;
        }

        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder withFlatNumber(String flatNumber) {
            this.flatNumber = flatNumber;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
