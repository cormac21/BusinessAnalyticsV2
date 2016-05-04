# BusinessAnalyticsV2

This project was created to implement a forecasting method using Holt Winter's formula as part of an exam for college;

How it works:
It forecasts values based on a given text based data set (separated by new lines as exemplified in sample file)

Arguments:
- FilePath (this is the path to the file you want to use as base)- String
- Forecasting period (how many iterations you wish to predict) - int
- Alpha (smoothing coeficient for level) - float 
- Beta (smoothing coeficient for tendency) - float
- Gamma (smoothing coeficient for period) - float

After the program runs it'll generate a txt file with the results in the directory where you indicated your base data file;
