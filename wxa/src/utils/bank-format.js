module.exports = function(a) {
  return a.replace(/\s/g,"").replace(/\d{4}/gi,
    function(a,b){return a+" "}).trim()
}
