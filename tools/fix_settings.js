const fs = require('fs');
const p = 'src/main/java/hex/api/baritone/api/Settings.java';
let src = fs.readFileSync(p, 'utf8');

const vals = {
  blockPlacementPenalty: '20.0',
  blockBreakAdditionalPenalty: '2.0',
  jumpPenalty: '2.0',
  walkOnWaterOnePenalty: '3.0',
  avoidBreakingMultiplier: '0.1',
  randomLooking113: '0.01',
  blockReachDistance: '4.5F',
  randomLooking: '0.01',
  costHeuristic: '3.563',
  backtrackCostFavoringCoefficient: '0.5',
  mobSpawnerAvoidanceCoefficient: '2.0',
  mobAvoidanceCoefficient: '1.5',
  maxCostIncrease: '0.0',
  pathCutoffFactor: '0.5',
  pathingMapLoadFactor: '0.75F',
  primaryTimeoutMS: '5000L',
  failureTimeoutMS: '2000L',
  planAheadPrimaryTimeoutMS: '8000L',
  planAheadFailureTimeoutMS: '5000L',
  slowPathTimeDelayMS: '250L',
  slowPathTimeoutMS: '1000L',
  pathRenderLineWidthPixels: '2.0F',
  goalRenderLineWidthPixels: '1.0F',
  cachedChunksOpacity: '0.5F',
  prefix: '"#"',
  breakCorrectBlockPenaltyMultiplier: '10.0',
  placeIncorrectBlockPenaltyMultiplier: '2.0',
  schematicFallbackExtension: '"schematic"',
  mineDropLoiterDurationMSThanksLouca: '250L',
  cachedChunksExpirySeconds: '1800L',
  yLevelBoxSize: '15.0',
  selectionOpacity: '0.5F',
  elytraFireworkSpeed: '1.6',
  elytraMinimumAvoidance: '0.0',
  elytraNetherSeed: '146008555100680L',
  elytraTimeBetweenCacheCullSecs: '3L'
};

const lines = src.split('\n');
const map = {};
for (const l of lines) {
  let m = l.match(/this\.(\w+) = new Settings\.Setting<>\(([^)]+)\)/);
  if (m && vals[m[1]] !== undefined) map[m[1]] = m[2].trim();
  m = l.match(/this\.(elytraTimeBetweenCacheCullSecs) = new Settings\.Setting<>\(TimeUnit\.MINUTES\.toSeconds\(([^)]+)\)/);
  if (m) map[m[1]] = m[2].trim();
}
let done = 0, missing = [];
const usedTokens = new Set(Object.values(map));
for (const [name, val] of Object.entries(vals)) {
  const tok = map[name];
  if (!tok) { missing.push(name); continue; }
  const re = new RegExp('(private static final [\\w.]+ ' + tok.replace(/[.*+?^${}()|[\]\\]/g, '\\$&') + ');');
  const before = src;
  src = src.replace(re, '$1 = ' + val + ';');
  if (src !== before) done++;
}
const declRe = /private static final ([\w.]+) ([^;]+);/g;
let m2;
const remaining = [];
while ((m2 = declRe.exec(src)) !== null) {
  if (!usedTokens.has(m2[2].trim())) remaining.push(m2[2]);
}
if (remaining.length === 1) {
  const tok = remaining[0];
  const re = new RegExp('(private static final String ' + tok.replace(/[.*+?^${}()|[\]\\]/g, '\\$&') + ');');
  const before = src;
  src = src.replace(re, '$1 = "Setting value cannot be null";');
  if (src !== before) done++;
}
fs.writeFileSync(p, src, 'utf8');
console.log('initialized: ' + done + '/' + Object.keys(vals).length + '; missing: ' + missing.join(',') + '; otherBlank: ' + remaining.join(','));
