# mutualfunddemo


Program outline
1.	Get the scheme number from user
2.	Download historic NAV data from mfapi site
3.	Get the period of investment and horizon from the user
4.	Compute trailing returns for entire horizon month over month for the latest date available in NAV data


Test Cases:

TC01:
  The url is null
TC02:
  The url returns no success response
  
TC03:
  The response json has no data
  
TC04:
  The response has null NAV data/ nav dates
  
TC05:
  When the period of Investment or horizon is zero/null
