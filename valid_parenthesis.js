const parenthesis = {
  '{':'}',
  '[':']',
  '(':')'
};

/**
* @param {string} s
* @return {boolean}
*/
var isValid = function(s) {
  let curr_pos = 0;
  const opening_parenthesis = [];

  while( curr_pos < s.length ){
    let curr_char = s.charAt(curr_pos);

    if( parenthesis.hasOwnProperty(curr_char) ) {
      opening_parenthesis.push(curr_char)
    } else {
      if( opening_parenthesis.length < 1){
        return false;
      }

      if ( parenthesis[opening_parenthesis[opening_parenthesis.length -1]] === curr_char ) {
        opening_parenthesis.pop();
      }
    }

    curr_pos = curr_pos + 1;
  }

  if(opening_parenthesis.length >= 1) {
    return false
  }

  return true;
};

console.log(isValid("{}"));
console.log(isValid("()[]{}"));
console.log(isValid("(]"));
console.log(isValid("([)]"));
console.log(isValid("{[]}"));