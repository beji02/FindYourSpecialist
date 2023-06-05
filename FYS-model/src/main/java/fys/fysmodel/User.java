package fys.fysmodel;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends Identifiable<Integer> {
    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String optionalDescription;
    @Column(unique = true)
    private String email;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reservation> myReservations = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rating> ratingsUser = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="user_recently_visited_announcements",
            joinColumns=@JoinColumn(name="user_id"),
            foreignKey=@ForeignKey(name="fk_user_recently_visited_announcements")
    )
    @MapKeyJoinColumn(name="announcement_id")
    @Column(name="timestamp")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<Announcement, LocalDateTime> recentlyVisitedAnnouncements = new HashMap<>();
    @CollectionTable(name = "user_favorite_specialists", joinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Specialist> favoriteSpecialists = new HashSet<>();
    @CollectionTable(name = "user_favorite_announcements", joinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Announcement> favoriteAnnouncements = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Notification> notifications = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.optionalDescription = optionalDescription;
    }

    public User(String username, String password, String email, String firstName, String lastName, LocalDate birthDate, String phoneNumber, String optionalDescription) {
        this(username, password, firstName, lastName, birthDate, phoneNumber, optionalDescription);
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOptionalDescription() {
        return optionalDescription;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public Set<Rating> getRatingsUser() {
        return ratingsUser;
    }

    public void setRatingsUser(Set<Rating> ratingsUser) {
        this.ratingsUser = ratingsUser;
    }

    public Set<Specialist> getFavoriteSpecialists() {
        return favoriteSpecialists;
    }

    public void setFavoriteSpecialists(Set<Specialist> favoriteSpecialists) {
        this.favoriteSpecialists = favoriteSpecialists;
    }

    public Set<Announcement> getFavoriteAnnouncements() {
        return favoriteAnnouncements;
    }

    public void setFavoriteAnnouncements(Set<Announcement> favoriteAnnouncements) {
        this.favoriteAnnouncements = favoriteAnnouncements;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addFavouriteSpecialist(Specialist specialist) {
        this.favoriteSpecialists.add(specialist);
    }

    public void removeFavouriteSpecialist(Specialist specialist) {
        this.favoriteSpecialists.remove(specialist);
    }

    public void addFavouriteAnnouncement(Announcement announcement) {
        this.favoriteAnnouncements.add(announcement);
    }

    public void removeFavouriteAnnouncement(Announcement announcement) {
        this.favoriteAnnouncements.remove(announcement);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void removeMessage(Message message) {
        this.messages.remove(message);
    }

    public void addRating(Rating rating) {
        this.ratingsUser.add(rating);
    }

    public void removeRating(Rating rating) {
        this.ratingsUser.remove(rating);
    }

    public void addRecentlyVisitedAnnouncement(Announcement announcement) {
        LocalDateTime timestamp = LocalDateTime.now();

        if(recentlyVisitedAnnouncements.containsKey(announcement)) {
            recentlyVisitedAnnouncements.put(announcement, timestamp);
            return;
        }
        recentlyVisitedAnnouncements.put(announcement, timestamp);

        if (recentlyVisitedAnnouncements.size() > 4) {
            Announcement oldestAnnouncement = null;
            LocalDateTime oldestTimestamp = LocalDateTime.MAX;

            for (Map.Entry<Announcement, LocalDateTime> entry : recentlyVisitedAnnouncements.entrySet()) {
                if (entry.getValue().isBefore(oldestTimestamp)) {
                    oldestAnnouncement = entry.getKey();
                    oldestTimestamp = entry.getValue();
                }
            }

            if (oldestAnnouncement != null) {
                recentlyVisitedAnnouncements.remove(oldestAnnouncement);
            }
        }
    }

    public void removeRecentlyVisitedAnnouncement(Announcement announcement) {
        recentlyVisitedAnnouncements.remove(announcement);
    }


    public Iterable<Announcement> getRecentlyVisitedAnnouncements() {
        return recentlyVisitedAnnouncements.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void setRecentlyVisitedAnnouncements(Map<Announcement, LocalDateTime> recentlyVisitedAnnouncements) {
        this.recentlyVisitedAnnouncements = recentlyVisitedAnnouncements;
    }

    public Map<Announcement, LocalDateTime> getRecentlyVisitedAnnouncementsMap() {
        return recentlyVisitedAnnouncements;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Reservation> getMyReservations() {
        return myReservations;
    }

    public void setMyReservations(Set<Reservation> myReservations) {
        this.myReservations = myReservations;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void readNotification(Notification notification) {
        notifications.remove(notification);
        notification.setRead(true);
        notifications.add(notification);
    }

}
