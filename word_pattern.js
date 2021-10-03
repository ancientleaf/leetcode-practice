/**
 * @param {string} pattern
 * @param {string} s
 * @return {boolean}
 */
 var wordPattern = function(pattern, s) {
  const patternList = pattern.split("");
  const wordList = s.split(" "); 

  if(patternList.length !== wordList.length) {
    return false;
  }

  let patternRep = convertToRepresentationPattern(patternList);
  let wordPatternRep = convertToRepresentationPattern(wordList);

  for( i = 0; i < patternRep.length; i++ ){
    if( patternRep[i] !== wordPatternRep[i]) {
      return false;
    }
  }
  return true;
};

function convertToRepresentationPattern (patternList) {
  let patternRepMap = {};
  let patternRepIter = 1;
  let patternRep = [];

  patternList.map( pattern => {
    if( ! patternRepMap.hasOwnProperty(pattern)) {
      patternRepMap[pattern] = patternRepIter;
      patternRepIter += 1;
    } 

    patternRep.push(patternRepMap[pattern]);
  })

  return patternRep;
} 


console.log(wordPattern("abba", "dog cat cat dog"));
console.log(wordPattern("abba", "dog cat cat fish"));
console.log(wordPattern("aaaa", "dog cat cat dog"));
console.log(wordPattern("abba", "dog dog dog dog"));
console.log(wordPattern("xyyzx", "dog cat cat man dog"));