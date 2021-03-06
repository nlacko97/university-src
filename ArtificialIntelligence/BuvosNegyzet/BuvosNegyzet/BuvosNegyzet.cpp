// BuvosNegyzet.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>

using namespace std;

class MagicSquare 
{
private:
	int count;
	int n;
	int MagicSum;
	int **square;
	int squares;
	bool *poss;

public:
	int getCount()
	{
		return count;
	}

	MagicSquare(int n) : n(n)
	{
		count = 0;
		squares = n * n;
		square = new int*[n];
		for (int i = 0; i < n; i++)
			square[i] = new int[n];
		MagicSum = (n*(pow(n, 2) + 1)) / 2;
		poss = new bool[n*n];
		for (int i = 0; i < n * n; i++)
			poss[i] = true;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				square[i][j] = 0;

	}

	void generate(int row, int col)
	{
		if (!validRow() || !validCol() || !validMainDiagonal() || !validMinorDiagonal())
			return;

		if (row == n)
		{
			count++;
			return;
		}

		if (col == n - 1)
		{
			int sum = MagicSum;
			for (int i = 0; i < n - 1; i++)
				sum -= square[row][i];
			if ((sum - 1 >= 0) && (sum - 1 < n*n) && poss[sum - 1])
			{
				square[row][col] = sum;
				poss[sum - 1] = false;
				generate(row + 1, 0);
				square[row][col] = 0;
				poss[sum - 1] = true;
			}
			else
			{
				square[row][col] = 0;
			}
		}
		else if (row == n - 1)
		{
			int sum = MagicSum;
			for (int i = 0; i < n - 1; i++)
				sum -= square[i][col];
			if ((sum - 1 >= 0) && (sum - 1 < n*n) && poss[sum - 1])
			{
				square[row][col] = sum;
				poss[sum - 1] = false;
				generate(row, col + 1);
				square[row][col] = 0;
				poss[sum - 1] = true;
			}
			else
			{
				square[row][col] = 0;
			}
		}
		else
		{
			for(int i = 0; i < squares; i++)
				if (poss[i])
				{
					square[row][col] = i + 1;
					poss[i] = false;
					int newRow = row, newCol = col + 1;
					if (newCol == n)
					{
						newCol = 0;
						newRow++;
					}
					generate(newRow, newCol);
					square[row][col] = 0;
					poss[i] = true;
				}
		}
	}

	bool validRow()
	{
		for (int i = 0; i < n; i++)
		{
			int sum = 0;
			bool inComplete = false;
			for (int j = 0; j < n; j++)
			{
				sum += square[i][j];
				if (square[i][j] == 0) inComplete = true;
			}
			if (!inComplete && sum != MagicSum)
				return false;
		}
		return true;
	}

	bool validCol()
	{
		for (int i = 0; i < n; i++)
		{
			int sum = 0;
			bool inComplete = false;
			for (int j = 0; j < n; j++)
			{
				sum += square[j][i];
				if (square[j][i] == 0) inComplete = true;
			}
			if (!inComplete && sum != MagicSum)
				return false;
		}
		return true;
	}

	bool validMainDiagonal()
	{
		int sum = 0;
		bool inComplete = false;
		for (int i = 0; i < n; i++)
		{
			sum += square[i][i];
			if (square[i][i] == 0) inComplete = true;
		}
		if (!inComplete && sum != MagicSum)
			return false;
		return true;
	}

	bool validMinorDiagonal()
	{
		int sum = 0;
		bool inComplete = false;
		for (int i = 0; i < n; i++)
		{
			sum += square[i][n - i - 1];
			if (square[i][n - i - 1] == 0) inComplete = true;
		}
		if (!inComplete && sum != MagicSum)
			return false;
		return true;
	}
};

int main()
{
	int n;
	cout << "Enter n for generating magic squares:" << endl;
	cin >> n;
	MagicSquare ms(n);
	ms.generate(0, 0);
	cout << "Total number of magic squares: " << ms.getCount() << endl;
	
    return 0;
}

