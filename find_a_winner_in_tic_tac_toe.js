// https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game/
"use strict";

/**
 * @param {number[][]} moves
 * @return {string}
 */
 var tictactoe = function(moves) {
  const INITIAL = 9999;
  const PLAYER_1_MOVE = 0, PLAYER_2_MOVE = 1;

  const BOARD_SIZE = 3;

  const board = [
    [INITIAL, INITIAL, INITIAL],
    [INITIAL, INITIAL, INITIAL],
    [INITIAL, INITIAL, INITIAL]
  ];

  for(let i = 0; i < moves.length; i++) {
    if(i % 2 == 0) {
      board[ moves[i][0]] [moves[i][1] ] = PLAYER_1_MOVE;
    } else {
      board[ moves[i][0]] [moves[i][1] ] = PLAYER_2_MOVE;
    }
  }

  let winner = '';
  let sum = INITIAL;

  // vertical
  for(let i = 0 ; i < BOARD_SIZE; i++) {
    sum = board[i][0] + board[i][1] + board[i][2];
    winner = getWinner(sum, PLAYER_1_MOVE, PLAYER_2_MOVE);
    if (winner !== '') 
      return printResult(BOARD_SIZE, moves, winner);
  };
  
  // hortizontal
  for( let i = 0 ; i < BOARD_SIZE; i++ ) {
    sum = board[0][i] + board[1][i] + board[2][i];
    winner = getWinner(sum, PLAYER_1_MOVE, PLAYER_2_MOVE);
    if (winner !== '') 
      return printResult(BOARD_SIZE, moves, winner);
  };

  // left to right diagonally
  sum = board[0][0] + board[1][1] + board[2][2];
  winner = getWinner(sum, PLAYER_1_MOVE, PLAYER_2_MOVE);

  if (winner !== '')
    return printResult(BOARD_SIZE, moves, winner);

  // right to left diagonally
  sum = board[0][2] + board[1][1] + board[2][0];
  winner = getWinner(sum, PLAYER_1_MOVE, PLAYER_2_MOVE);

  return printResult(BOARD_SIZE, moves, winner);
};

function printResult(BOARD_SIZE, moves, winner) {

  let output = '';

  if( moves.length < BOARD_SIZE * BOARD_SIZE && winner === '') {
    output = "Pending";
    return output;
  } 
  
  if (BOARD_SIZE * BOARD_SIZE === moves.length) {
    if( winner === '' ) {
      output = 'Draw'
      return output;
    }
  }

  return winner;
}

function getWinner(sum, PLAYER_1_MOVE, PLAYER_2_MOVE) {
  if(sum === PLAYER_1_MOVE * 3) {
    return 'A'
  } else if ( sum === PLAYER_2_MOVE * 3 ) {
    return 'B'
  }
  return '';
}

console.log( tictactoe([[0,0],[2,0],[1,1],[2,1],[2,2]]) );