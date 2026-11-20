package com.smartsplitter;

import java.util.Objects;

public class User {
    private final String id;
    private final String name;

    public User(String id, String name) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("User ID and name cannot be empty.");
        }
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return id.equalsIgnoreCase(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id.toLowerCase()); }

    @Override
    public String toString() { return name + " (" + id + ")"; }
}
