const fs = require('fs');
const lines = fs.readFileSync('build_err.txt', 'utf8').split(/\r?\n/);
const errs = [];
let cur = null;
for (const l of lines) {
  const m = l.match(/^(?:\.\\gradlew\.bat : )?(C:\\[^:]+\.java):(\d+): (error|warning): (.*)$/);
  if (m) {
    cur = { f: m[1], n: +m[2], kind: m[3], msg: m[4] };
    errs.push(cur);
  } else if (cur && /^\s+/.test(l) && l.trim().length && !l.includes('^') && !/^\.\\gradlew/.test(l)) {
    cur.msg += ' ' + l.trim();
  }
}
const seen = new Set();
const out = [];
for (const er of errs) {
  const key = er.f + ':' + er.n + ':' + er.msg;
  if (seen.has(key)) continue;
  seen.add(key);
  const src = fs.readFileSync(er.f, 'utf8').split(/\r?\n/);
  out.push(er.msg + ' | ' + er.f.split('\\').pop() + ':' + er.n + ' | ' + (src[er.n - 1] || '').trim());
}
fs.writeFileSync('err_triage.txt', out.join('\n'), 'utf8');
console.log('errors: ' + out.length);
