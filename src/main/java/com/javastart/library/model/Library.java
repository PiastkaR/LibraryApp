package com.javastart.library.model;

import com.javastart.library.exception.PublicationAlreadyExistsException;
import com.javastart.library.exception.UserAlreadyExistException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {
    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }
    public void addUser(LibraryUser user) throws UserAlreadyExistException {
        if(users.containsKey(user.getPesel()))
            throw new UserAlreadyExistException(
                    "User already exists: " + user.getPesel()
            );
        users.put(user.getPesel(), user);
    }

    public void addPublication(Publication publication) throws PublicationAlreadyExistsException {
        if (publications.containsKey(publication.getTitle()))
            throw new PublicationAlreadyExistsException(
                    "Publication already exists: " + publication.getTitle()
            );
        publications.put(publication.getTitle(), publication);
    }

    public boolean removePublication(Publication publication) {
        if (publications.containsValue(publication)) {
            publications.remove(publication.getTitle());
            return true;
        } else {
            return false;
        }
    }

    public Collection<Publication>getSortedPublications(Comparator<Publication> comparator) {
        ArrayList<Publication>list = new ArrayList<>(this.publications.values());
        list.sort(comparator);

        return list;
    }

    public Collection<LibraryUser>getSortedUsers(Comparator<LibraryUser> comparator){
        ArrayList<LibraryUser>list = new ArrayList<>(this.users.values());
        list.sort(comparator);

        return list;
    }
}