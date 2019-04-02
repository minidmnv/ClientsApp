package pl.mnicinski.ClientsApp.domain.client;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import org.hibernate.annotations.GenericGenerator;
import pl.mnicinski.ClientsApp.domain.client.address.Address;

import javax.persistence.*;
import java.util.*;

import static pl.mnicinski.ClientsApp.domain.client.validation.MailValidator.hasAtSign;
import static pl.mnicinski.ClientsApp.domain.validation.CommonObjectValidator.isNull;
import static pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator.longerThen;
import static pl.mnicinski.ClientsApp.domain.validation.CommonStringValidator.notNull;

@Entity
public final class Client {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private final UUID uuid;
    private final String login;
    private final String mailAddress;
    @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private final Set<Address> addresses;

    /**
     * Default constructor
     */
    private Client() {
        this.uuid = null;
        this.login = null;
        this.mailAddress = null;
        this.addresses = null;
    }

    private Client(Builder builder) {
        uuid = builder.uuid;
        login = builder.login;
        mailAddress = builder.mailAddress;
        addresses = builder.addresses;
    }

    public boolean validateNew() {
        isNull().validate(uuid).throwIfInvalid("uuid for new values");
        validate();

        return true;
    }

    public boolean validate() {
        notNull()
                .and(longerThen(3))
                .validate(login).throwIfInvalid("login");
        notNull()
                .and(longerThen(5))
                .and(hasAtSign())
                .validate(mailAddress).throwIfInvalid("mailAddress");

        if(addresses != null) {
            addresses.forEach(Address::validateNew);
        }

        return true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public Client appendNewAddress(Address address) {
        return new Builder(this).withAddress(address).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(uuid, client.uuid) &&
                login.equals(client.login) &&
                mailAddress.equals(client.mailAddress) &&
                addresses.equals(client.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, login, mailAddress, addresses);
    }

    public static final class Builder {
        private UUID uuid;
        private String login;
        private String mailAddress;
        private Set<Address> addresses;

        public Builder() {
        }

        public Builder(Client copy) {
            this.uuid = copy.uuid;
            this.login = copy.login;
            this.mailAddress = copy.mailAddress;
            if(copy.addresses != null) {
                this.addresses = new HashSet<>(copy.addresses);
            }
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withMailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
            return this;
        }

        public Builder withAddresses(Set<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Client build() {
            return new Client(this);
        }

        public Builder withAddress(Address address) {
            if(addresses == null) {
                addresses = new HashSet<>();
            }
            addresses.add(address);
            return this;
        }
    }
}
