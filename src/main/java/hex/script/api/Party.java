package hex.script.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Группа клиента, только чтение: состав, лидер и имя. Обмен произвольными
 * сообщениями между скриптами участников пока не поддерживается.
 *
 * <pre>{@code
 * if (party().inParty()) {
 *     for (PartyMember member : party().members()) {
 *         chat(member.login() + " — " + member.role());
 *     }
 * }
 * }</pre>
 */
public final class Party {

    /** Состояние: 0 — есть ли соединение, 1 — в группе ли. */
    @FunctionalInterface
    public interface StateOp {
        boolean run(int query);
    }

    /** Имя группы; пустая строка вне группы. */
    @FunctionalInterface
    public interface NameOp {
        String run();
    }

    /** Снимок участников: логин, ник и роль подряд. */
    @FunctionalInterface
    public interface MembersOp {
        String[] run();
    }

    private final StateOp stateOp;
    private final NameOp nameOp;
    private final MembersOp membersOp;

    public Party(StateOp stateOp, NameOp nameOp, MembersOp membersOp) {
        this.stateOp = stateOp;
        this.nameOp = nameOp;
        this.membersOp = membersOp;
    }

    /**
     * @return есть ли живое соединение с сервером клиента
     */
    public boolean connected() {
        return stateOp.run(0);
    }

    /**
     * @return состоишь ли ты сейчас в группе
     */
    public boolean inParty() {
        return stateOp.run(1);
    }

    /**
     * @return имя группы или пустая строка
     */
    public String name() {
        return nameOp.run();
    }

    /**
     * @return снимок участников, включая тебя
     */
    public List<PartyMember> members() {
        String[] raw = membersOp.run();
        if (raw == null) {
            return List.of();
        }
        List<PartyMember> result = new ArrayList<>(raw.length / 3);
        for (int i = 0; i + 3 <= raw.length; i += 3) {
            result.add(new PartyMember(raw[i], raw[i + 1], raw[i + 2]));
        }
        return result;
    }

    /**
     * @param login логин аккаунта клиента
     * @return участник или {@code null}
     */
    public PartyMember member(String login) {
        for (PartyMember member : members()) {
            if (member.login().equalsIgnoreCase(login)) {
                return member;
            }
        }
        return null;
    }
}
