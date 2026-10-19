const fs = require('fs');
const f = 'src/main/java/sg/ec/WorldHelper.java';
let s = fs.readFileSync(f, 'utf8');

// drop broken imports
s = s.replace(/^import sg\.ec\.5Y3ziFwLpwmYegKH\.\S+;\r?\n/gm, '');

// class renames
s = s.split('5bBkC0fhj2Q8h9bz').join('TrapMarker');
s = s.split('fEEsjlIyg7aYDL47').join('RecentBlock');
s = s.split('QLj4EFKQ3ej3X0Aa').join('BlockMap');
s = s.split('hZnjm1pQrEw3G4DU').join('TrapType');
s = s.split('new BlockMap(this)').join('new BlockMap()');

// member renames
s = s.split('.Б()').join('.type()');
s = s.split('.西()').join('.position()');
s = s.split('.г()').join('.totalTicks()');
s = s.split('.юю()').join('.elapsedTicks()');
s = s.split('.с()').join('.id()');
s = s.split('.х必()').join('.tick()');
s = s.split('.б()').join('.item()');
s = s.split('var0.бl >= var0.б6').join('var0.elapsed >= var0.lifetime');

const nested = `
   enum TrapType {
      STAN("Стан", BossEvent.BossBarColor.YELLOW),
      TRAP("Трап", BossEvent.BossBarColor.RED),
      BIG_TRAP("Биг-трап", BossEvent.BossBarColor.RED),
      PLAST("Пласт", BossEvent.BossBarColor.BLUE),
      PLAST_WALL("Пласт-стена", BossEvent.BossBarColor.BLUE),
      REALLYWORLD_TRAP("Реалворлд-трап", BossEvent.BossBarColor.PURPLE);

      private final String label;
      private final BossEvent.BossBarColor color;

      TrapType(String var1, BossEvent.BossBarColor var2) {
         this.label = var1;
         this.color = var2;
      }

      public String label() {
         return this.label;
      }

      public BossEvent.BossBarColor color() {
         return this.color;
      }
   }

   static final class TrapMarker {
      private final TrapType type;
      private final Vec3 position;
      private final UUID id = UUID.randomUUID();
      private final int lifetime = 400;
      int elapsed;

      TrapMarker(TrapType var1, Vec3 var2) {
         this.type = var1;
         this.position = var2;
      }

      TrapType type() {
         return this.type;
      }

      Vec3 position() {
         return this.position;
      }

      UUID id() {
         return this.id;
      }

      int totalTicks() {
         return this.lifetime;
      }

      int elapsedTicks() {
         return this.elapsed;
      }

      void tick() {
         this.elapsed++;
      }

      ItemStack item() {
         return switch (this.type) {
            case STAN -> new ItemStack(Items.EVOKER_SPAWN_EGG);
            case TRAP, BIG_TRAP -> new ItemStack(Items.RESPAWN_ANCHOR);
            case PLAST, PLAST_WALL -> new ItemStack(Items.PISTON);
            case REALLYWORLD_TRAP -> new ItemStack(Items.END_CRYSTAL);
         };
      }
   }

   static final class RecentBlock {
      private final int x;
      private final int y;
      private final int z;
      private final long time;
      private final Block block;

      RecentBlock(int var1, int var2, int var3, long var4, Block var5) {
         this.x = var1;
         this.y = var2;
         this.z = var3;
         this.time = var4;
         this.block = var5;
      }

      int x() {
         return this.x;
      }

      int y() {
         return this.y;
      }

      int z() {
         return this.z;
      }

      long time() {
         return this.time;
      }

      Block block() {
         return this.block;
      }
   }

   static final class BlockMap extends HashMap<Long, Block> {
   }
}
`;

// replace the final closing brace
const idx = s.lastIndexOf('}');
s = s.slice(0, idx) + nested.trimStart();
fs.writeFileSync(f, s);
console.log('WorldHelper rewritten');
