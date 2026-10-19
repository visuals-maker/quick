package hex.script.api;

/**
 * Участник группы клиента.
 */
public final class PartyMember {

    private final String login;
    private final String name;
    private final String role;

    PartyMember(String login, String name, String role) {
        this.login = login;
        this.name = name;
        this.role = role;
    }

    /**
     * @return логин аккаунта клиента
     */
    public String login() {
        return login;
    }

    /**
     * @return игровой ник, если он известен
     */
    public String name() {
        return name;
    }

    /**
     * @return роль в клиенте
     */
    public String role() {
        return role;
    }

    @Override
    public String toString() {
        return login + " (" + role + ")";
    }
}
