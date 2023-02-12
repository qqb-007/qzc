var extend = function (target, source) {
	for (var key in source) {
		var val = source[key];
		target[key] = val;
	}
	return target;
}

module.exports = extend;