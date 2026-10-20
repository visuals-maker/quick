package quick.profile;

import quick.profile.objects.RoleObject;

public final class ProfileAPI {
	private static ProfileAPI instance = new ProfileAPI(new Model("Player", "local", RoleObject.DEFAULT));

	private final Model model;

	private ProfileAPI(Model var1) {
		this.model = var1;
	}

	public static ProfileAPI get() {
		return instance;
	}

	public static ProfileAPI create(String var0, int var1, RoleObject var2) {
		instance = new ProfileAPI(new Model(var0, var0, var2));
		return instance;
	}

	public Model getModel() {
		return this.model;
	}

	public static final class Model {
		private final String name;
		private final String userIdentifier;
		private final RoleObject role;

		Model(String var1, String var2, RoleObject var3) {
			this.name = var1;
			this.userIdentifier = var2;
			this.role = var3;
		}

		public String getName() {
			return this.name;
		}

		public String getUserIdentifier() {
			return this.userIdentifier;
		}

		public RoleObject getRole() {
			return this.role;
		}
	}
}
