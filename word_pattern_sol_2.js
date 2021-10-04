/**
 * @param {string} pattern
 * @param {string} s
 * @return {boolean}
 */
 var wordPattern = function(pattern, s) {
  let patternMap = {};

  const patternList = pattern.split('');
  const wordList = s.split(" ");

  if( patternList.length !== wordList.length ) {
    return false;
  }

  for( i = 0; i < patternList.length; i++) {
    if( ! patternMap.hasOwnProperty(patternList[i]) ) {

      if( Object.keys(patternMap).filter( key => patternMap[key] === wordList[i] ).length === 0 )
        patternMap[patternList[i]] = wordList[i];
      else 
        return false;
      
    } else {
      if ( patternMap[patternList[i]] !== wordList[i] )
        return false;
    }
      
  }

  return true;
  
};

console.log(wordPattern("abba", "dog cat cat dog"));
console.log(wordPattern("abba", "dog cat cat fish"));
console.log(wordPattern("aaaa", "dog cat cat dog"));
console.log(wordPattern("abba", "dog dog dog dog"));
console.log(wordPattern("xyyzx", "dog cat cat man dog"));