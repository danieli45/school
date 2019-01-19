//
//  main.cpp
//  DiscreteStructures
//
//  Created by Daniel Chavez on 12/2/18.
//  Copyright © 2018 Daniel Chavez. All rights reserved.
//  Dr. Miller Lawrence
//

#include <cstdlib>
#include <iostream>
using namespace std;
//const int MAX = 8;
//const int MAX = 16;
const int MAX = 32;
//const int MAX = 64;
//const int MAX = 128;
//const int MAX = 256;
//const int MAX = 512;
//const int MAX = 1024;
//const int MAX = 2048;
//const int MAX = 4096;
//const int MAX = 8192;
//const int MAX = 16384;
//const int MAX = 32768;
//const int MAX = 65536;
//const int MAX = 131072;
//const int MAX = 262144;
//const int MAX = 524288;
//const int MAX = 1048576;
int data[MAX];

void swap(int data[], int i, int j);
void Bubblesort(int data[], int n);
void Selectionsort(int data[], int n);
void Insertionsort(int data[], int n);
void Mergesort(int data[], int n);
void Quicksort(int data[], int n);

void initialize()
{
    srand(1);
    for (int i = 0; i < MAX; i++)
    {
        data[i]=rand()%MAX;
        
    }
}

void verify(int data[], int size)
{
    for (int i = 0; i < size-1; i++)
    {
        if (data[i] > data[i+1])
        {
            cout << "array not sorted - data[" << i << "]=" << data[i] <<"  data[";
            cout << i+1 << "]=" << data[i+1] <<endl;
            return;
        }
    }
    cout << "array sorted" << endl;
}

void display(int data[], int p, int r)
{
    for (int i = p; i <= r; i++)
    {
        cout << data[i] << " ";
    }
    cout << endl;
}

void swap(int data[], int i, int j, long &count)
{
    int temp = data[i];
    data[i] = data[j];
    data[j] = temp;
}

void Bubblesort(int data[], int n, long &count)
{
    for (int i = 0; i < n-1; i++)
    {
        for (int j = 0; j < n-i-1; j++)
        {
            if (data[j] > data[j+1])
            {
                swap(data, j, j+1, count);
                count++;
            }
        }
    }
}

void Insertionsort(int data[], int n, long &count)
{
    // the following line of code has been fixed
    for (int j = 1; j < n; j++)
    {
        int i = 0;
        while (data[j] > data[i])
        {
            i++;
        }
        int m = data[j];
        for (int k = 0; k < j-i; k++)
        {
            data[j-k] = data[j-k-1];
        }
        data[i] = m;
        count++;
    }
}

void Merge(int data[], int p, int m, int r, long &count)
{
    int size = r - p + 1;
    int temp[size];
    int Tindex=0;
    int Lindex=p;
    int Rindex=m+1;
    while ((Lindex <= m) && (Rindex <= r))
    {
        if (data[Lindex] <= data[Rindex])
        {
            temp[Tindex] = data[Lindex];
            Tindex++;Lindex++;
        }
        else
        {
            temp[Tindex] = data[Rindex];
            Tindex++;
            Rindex++;
        }
    }
    for (int i=Lindex; i<=m; i++)
    {
        temp[Tindex] = data[i];
        Tindex++;
    }
    for (int j=Rindex; j<=r; j++)
    {
        temp[Tindex] = data[j];
        Tindex++;
    }
    for (int i = p; i <= r; i++)
    {
      data[i] = temp[i-p];
    }
}

void MergesortHelper(int data[], int p, int r, long &count)
{
    if (p < r)
    {
        int m = p + (r-p)/2;
        MergesortHelper(data, p, m, count);
        MergesortHelper(data, m+1, r, count);
        Merge(data, p, m, r, count);
        count++;
        
    }
}

void Mergesort(int data[], int n, long &count)
{
    MergesortHelper(data, 0, n-1, count);

}

int partition(int data[], int p, int r, long &count)
{
    int i, j, k, l;
    int x = data[r];
    i = p - 1;
    for (j = p; j < r; j++)
    {
        if (data[j] <=x)
        {
            i++;
            swap(data, i, j, count);
        }
    }
    swap(data, i+1, r, count);
    return (i+1);
}

int median3partition(int data[], int p, int r, long &count)
{
    int i, j, k, l;
    i = (rand() % (r+1-p)) + p;
    j = (rand() % (r+1-p)) + p;
    k = (rand() % (r+1-p)) + p;
    if (((data[i] <= data[j]) && (data[j] <= data[k])) || ((data[k] <= data[j]) && (data[j] <= data[i])))
        l = j;
    else if (((data[i] <= data[k]) && (data[k] <= data[j])) || ((data[j] <= data[k]) && (data[k] <= data[i])))
        l = k;
    else if (((data[j] <= data[i]) && (data[i] <= data[k])) || ((data[k] <= data[i]) && (data[i] <= data[j])))
        l = i;
    swap(data, r, l, count);
    return partition(data, p, r, count);
}

void squicksort(int data[], int p, int r, long &count)
{
    int q;
    if (p < r)
    {
        q = median3partition(data, p, r, count);
        squicksort(data, p, q-1, count);
        squicksort(data, q+1, r, count);
        count++;
    }
}

void Quicksort(int data[], int n, long &count)
{
    srand(time(NULL));
    squicksort(data, 0, n-1, count);
}

int main()
{
    long count = 0;
    count = 0;
    cout << endl << endl;
    cout << "*************************************************************************" << endl;
    
 cout << "Initializing array" << endl;
    initialize();
    //display(data, 0, MAX-1);
    cout << endl;
    cout << "Quicksorting array" << endl;
    Quicksort(data, MAX, count);
    verify(data,MAX);
    //display(data, 0, MAX-1);
    cout << "Quicksort performed " << count << " array accesses" << endl;
    cout << endl;
    count = 0;
    cout << endl << endl;
    
    cout << "**************************************************************************" << endl;
    
    cout << "Initializing array" << endl;
    initialize();
    //display(data, 0, MAX-1);
    cout << endl;
    cout << "Merge Sorting array" << endl;
    Mergesort(data, MAX, count);
    verify(data,MAX);
    //display(data, 0, MAX-1);
    cout << "Mergesort performed " << count << " array accesses" << endl;
    cout << endl;
    cout << endl << endl;
    cout << "**************************************************************************" << endl;
    
    cout << "Initializing array" << endl;
    initialize();
    //display(data, 0, MAX-1);
    cout << endl;
    cout << "Insertion Sorting array" << endl;
    Insertionsort(data, MAX, count);
    verify(data,MAX);
    //display(data, 0, MAX-1);
    cout << "Insertionsort performed " << count << " array accesses" << endl;
    cout << endl;
    count = 0;
    cout << endl << endl;
    cout << "**************************************************************************" << endl;
    
    cout << "Initializing array" << endl;
    initialize();
    //display(data, 0, MAX-1);
    cout << endl;
    cout << "Bubble Sorting array" << endl;
    Bubblesort(data, MAX, count);
    verify(data,MAX);
    //display(data, 0, MAX-1);
    cout << "Bubblesort performed " << count << " array accesses" << endl;
    cout << endl;
    
}
    
