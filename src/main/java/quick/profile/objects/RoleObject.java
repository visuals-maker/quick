package quick.profile.objects;

public enum RoleObject {
	DEFAULT,
	BETA,
	ADMIN;

	public boolean priorityHigherOrEquals(RoleObject var1) {
		return this.ordinal() >= var1.ordinal();
	}
}
