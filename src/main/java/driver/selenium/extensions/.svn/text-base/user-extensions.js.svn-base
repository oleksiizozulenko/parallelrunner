/**
 * storeValue, storeText, storeAttribute and store actions now 
 * have 'global' equivalents.
 * Use storeValueGlobal, storeTextGlobal, storeAttributeGlobal or storeGlobal
 * will store the variable globally, making it available it subsequent tests.
 *
 * See the Reference.html for storeValue, storeText, storeAttribute and store
 * for the arguments you should send to the new Global functions.
 *
 * example of use
 * in testA.html:
 * +------------------+----------------------+----------------------+
 * |storeGlobal       | http://localhost/    | baseURL              |
 * +------------------+----------------------+----------------------+
 * 
 * in textB.html (executed after testA.html):
 * +------------------+-----------------------+--+
 * |open              | ${baseURL}Main.jsp    |  |
 * +------------------+-----------------------+--+
 *
 * Note: Selenium.prototype.replaceVariables from selenium-api.js has been replaced
 *       here to make it use global variables if no local variable is found.
 *       This might cause issues if you upgraded Selenium in the future and this function 
 *       has been changed.
 *
 * @author Guillaume Boudreau
 */
 
globalStoredVars = new Object();

/*
 * Globally store the value of a form input in a variable
 */
Selenium.prototype.doStoreValueGlobal = function(target, varName) {
    if (!varName) {
        // Backward compatibility mode: read the ENTIRE text of the page
        // and stores it in a variable with the name of the target
        value = this.page().bodyText();
        globalStoredVars[target] = value;
        return;
    }
    var element = this.page().findElement(target);
    globalStoredVars[varName] = getInputValue(element);
};

/*
 * Globally store the text of an element in a variable
 */
Selenium.prototype.doStoreTextGlobal = function(target, varName) {
    var element = this.page().findElement(target);
    globalStoredVars[varName] = getText(element);
};

/*
 * Globally store the value of an element attribute in a variable
 */
Selenium.prototype.doStoreAttributeGlobal = function(target, varName) {
    globalStoredVars[varName] = this.page().findAttribute(target);
};

/*
 * Globally store the result of a literal value
 */
Selenium.prototype.doStoreGlobal = function(value, varName) {
    globalStoredVars[varName] = value;
};

/*
 * Search through str and replace all variable references ${varName} with their
 * value in storedVars (or globalStoredVars).
 */
Selenium.prototype.replaceVariables = function(str) {
    var stringResult = str;

    // Find all of the matching variable references
    var match = stringResult.match(/\$\{\w+\}/g);
    if (!match) {
        return stringResult;
    }

    // For each match, lookup the variable value, and replace if found
    for (var i = 0; match && i < match.length; i++) {
        var variable = match[i]; // The replacement variable, with ${}
        var name = variable.substring(2, variable.length - 1); // The replacement variable without ${}
        var replacement = storedVars[name];
        if (replacement != undefined) {
            stringResult = stringResult.replace(variable, replacement);
        }
        var replacement = globalStoredVars[name];
        if (replacement != undefined) {
            stringResult = stringResult.replace(variable, replacement);
        }
    }
    return stringResult;
};


/**
 * storeTableContent(searchText/searchCol/contentCol/contentPat/contentPos, storeVar)
 * storeTableContent allows to read the content from one column of a table
 * when a matching found in another column of the same same row. 
 *
 * This is a very useful functionality in a senario like:
 * Assume a table of users with an 'Edit' link infront of each name
 * We want to search through the name list and want to click the corresponding 
 * 'Edit' link in front of the name.
 * 
 * storeGlobalTableContent also can be enabled if you install 'global' 
 * user extension.
 *
 * example of use:
 * +------------------+---------------------------+-----------+
 * |storeTableContent | sumith/0/1/userId=(\w+)/1 | varUserId |
 * +------------------+---------------------------+-----------+
 * 
 * Note: Selenium accepts only two parameters first as input and second as
 *       output variable. But in this function we need to pass 5 parameters
 *       to the function. Simple work-arround I did is simply to separate
 *       parameters by forward-slash ("/"). This works fine for many general
 *       senarios (Thanks to the power of regular expressions). But there will 
 *       issues need to pay speciall attention if you use complex 'searchText'
 *       and complex 'contentPattern's.
 *
 * Five parameters: searchText/searchCol/contentCol/contentPat/contentPos
 *       searchText: Text you are looking in the Table
 *       searchcol: Searching column number (start count from 0)
 *       contentCol: Column number of the content 
 *       contentPat: Pattern used to grab only the required portion of 
 *                   the column content. (.* - catch all the content)
 *       contentPos: The matching group to return (0 - for complete match)
 *
 *
 * storeTableRow(searchText/searchCol/minCol, storeVar)
 * storeTableRow returens (store to a variable) row number in which we can a 
 * given text. (Please note that row count start from 0).
 *
 * storeGlobalTableRow also can be enabled if you install 'global' user extension.
 *
 * example of use:
 * +--------------+-------------------------+-------------+
 * |storeTableRow | contentToBeSearched/0/1 | varTableRow |
 * +--------------+-------------------------+-------------+
 * 
 * Note: Selenium accepts only two parameters first as input and second as
 *       output variable. But in this function we need to pass 3 parameters
 *       to the function (including the optional 3rd parameter). Simple 
 *       work-arround I did is simply to separate parameters by forward-slash 
 *       ("/"). This works fine for many general senarios (Thanks to the power 
 *       of regular expressions). But there will issues need to pay speciall 
 *       attention if you use complex 'searchText' and complex 'contentPattern's.
 *
 * Three parameters: searchText/searchCol/minCol
 *       searchText: Text you are looking in the Table
 *       searchcol: Searching column number (start count from 0)
 *       minCol: Tables having columns less than minCol with ignore
 *
 * @author Sumith Gamage
 */

// Search the row of a table with a given content and minimal number of colomns
function searchTableRow(searchText, searchCol, minCol) {
        
    // Get all table row objects
    rowObj = selenium.browserbot.getCurrentWindow().document.getElementsByTagName("TR"); 
    
    // Add a preceeding backslash to the special charactors.
    // This eleminates the problem of matching "${passVar}" 
    // like values. Currently we have only '$' sign which give 
    // troubles. But later we may have to add more charactors.
    // - Sumith (14th Dec, 2006)
    searchText = searchText.replace(/(\$)/g, "\\$1");
    
    // Loop through table rows
    var foundContent = false;
    for (rowNo=0; rowNo<rowObj.length; rowNo++) {
        // No need to read too short rows
        var maxCol = rowObj[rowNo].cells.length;
        if (maxCol <= searchCol || maxCol <= minCol) {continue;}
        
        // Read the content of the search colomn
        searchHtml = rowObj[rowNo].cells[searchCol].innerHTML;
        
        // Let's ignore <TD> inside <TD>s.
        // Due to some odd reasons 'm' switch does not work in 'match'
        // - Sumith (10th Dec, 2006)
        if (searchHtml.replace(/\n/g, " ").match(/<TD.*>.*<\/TD>/i)) continue;
        
        // Build the pattern to check search column
        var searchPat = new RegExp("^(.*<[^<>]*>)?\\s*"+searchText+"\\s*(<[^<>]*>.*)?$", "m");   
        
        // No more processing for unmatched rows
        if (!searchHtml.match(searchPat)) {continue;} 
        
        // Got the row! Let's simply return it
        return rowNo;
    }
    
    return -1; // No row matched the criteria
}

// Find the row of a table with a given content
function getTableRow(value) {
    
    // Seperating parameters passed in value
    var paraPart = [];
    paraPart = (value.match(/(.*)\/(\d+)\/(\d+)/)) ? value.match(/(.*)\/(\d+)\/(\d+)/) : value.match(/(.*)\/(\d+)/);
    var searchText = paraPart[1];
    var searchCol  = paraPart[2];
    var minCol = (paraPart.length > 2) ? paraPart[3]-1 : 0;
    
    return searchTableRow(searchText, searchCol, minCol);
}

function getTableContent(value) {
    
    // Seperating 5 parameters passed in value
    var paraPart = value.match(/(.*)\/(\d+)\/(\d+)\/(.*)\/(\d+)/);
    var searchText = paraPart[1];
    var searchCol  = paraPart[2];
    var contentCol = paraPart[3];
    var contentPatStr = paraPart[4];
    var contentPos = paraPart[5];
    
    var rowNo = searchTableRow(searchText, searchCol, contentCol);
    
    if (rowNo < 0) {return false;} // No row was matched
    
    // Get all table row objects
    var rowContent = selenium.browserbot.getCurrentWindow().document.getElementsByTagName("TR")[rowNo]; 
    
    // Get the corresponding content html
    contentHtml = rowContent.cells[contentCol].innerHTML; 
    
    // It was noted that when we have links <A ...> ... </A> in 
    // contentHtml, some funny charactors appers in the URL in href 
    // closure. Following two statements are a work arrond for it.
    // - Sumith (30th Nov, 2006)
    
    // This removes %20s & %0As at the begining of the URL. We will
    // need to modify this later, if we see any such charactor at 
    // the middle way of the URL too.
    contentHtml = contentHtml.replace(/(href=\"?)(%20|%0A)*/gi, "$1");
    // replaces "&amp;" with "&"
    contentHtml = contentHtml.replace(/&amp;/gi, "&"); 
    // merge multiple lines and remove spaces
    contentHtml = contentHtml.replace(/^\s+|\s+$|\n/g, " "); 
    
    var contentPat = new RegExp(contentPatStr); // Build the content pattern
    var foundContent = (contentHtml.match(contentPat)) ? contentHtml.match(contentPat)[contentPos] : false; // Retrieving the content
    
    return foundContent;
}

Selenium.prototype.doStoreTableContent = function(value, varName) {
    storedVars[varName] = getTableContent(value);
};

Selenium.prototype.doStoreTableRow = function(value, varName) {
    storedVars[varName] = getTableRow(value) + 1;
};

// Please uncomment following code-lines only if you have installed
// 'global' (http://wiki.openqa.org/display/SEL/global) user extension.

Selenium.prototype.doStoreGlobalTableRow = function(value, varName) {
    globalStoredVars[varName] = getTableRow(value);
};

Selenium.prototype.doStoreGlobalTableContent = function(value, varName) {
    globalStoredVars[varName] = getTableContent(value);
};

Selenium.prototype.doDisplayAlert = function(value, varName) {
    alert(value);
};

Selenium.prototype.getTableRowsCount = function(locator) {
  /**
   * Gets the number of rows in a table.
   *
   * @param locator element locator for table
   * @return number of rows in the table, 0 if none
   */

    var table = this.browserbot.findElement(locator);
    return table.rows.length.toString();
};

//
// Locates an element by a partial match on id
//
PageBot.prototype.locateElementByPartialId = function(text, inDocument) {
    return this.locateElementByXPath("//*[contains(./@id, 'Z')][1]".replace(/Z/,text), inDocument);
};

var consoleMessages = [];

if (browserVersion.isChrome) {
	  var theConsoleListener = {
	     stepIdx:0, //beginJsErrorChecker command index
	     observe:function( aMessage ){//async!
	        dump("Log : " + aMessage.message);
	        //Error, Warning, Message too noise. We only report "[JavaScript Error:..."
	        if(aMessage.message != null && aMessage.message.indexOf("[JavaScript Error:") == 0){ 
	          LOG.error("JsErrorChecker:" + aMessage.message);
	          consoleMessages.push(aMessage.message);
	        //  alert( consoleMessages);
	       //   alert( consoleMessages);
	      } 
	   },
	   QueryInterface: function (iid) {
	        if (!iid.equals(Components.interfaces.nsIConsoleListener) &&
	              !iid.equals(Components.interfaces.nsISupports)) {
	            throw Components.results.NS_ERROR_NO_INTERFACE;
	            }
	        return this;
	   }
	  }; 
	}

Selenium.prototype.doTestFunction = function() {
    alert("Hello World");
};

Selenium.prototype.doBeginJsErrorChecker = function(){
	  try {
	    if (browserVersion.isChrome) {// firefox 
	        //  theConsoleListener.stepIdx=testCase.debugContext.debugIndex;//set current step idx for async call
	          var aConsoleService = Components.classes["@mozilla.org/consoleservice;1"]
	                          .getService(Components.interfaces.nsIConsoleService);
	          aConsoleService.registerListener(theConsoleListener);
	          aConsoleService.reset();
	    }
	    } catch (e) {
	    throw new SeleniumError("Threw an exception: " + e.message);
	    }
	};

Selenium.prototype.doEndJsErrorChecker = function(){
	  try {
	    if (browserVersion.isChrome) {// firefox  
	          var aConsoleService = Components.classes["@mozilla.org/consoleservice;1"]
	                          .getService(Components.interfaces.nsIConsoleService);
	          aConsoleService.unregisterListener(theConsoleListener);
	          aConsoleService.reset();
	    }
	    } catch (e) {
	    throw new SeleniumError("Threw an exception: " + e.message);
	    }
	};

Selenium.prototype.getRetrieveAllJSErrors= function(){
LOG.info("retriveJSError: " + consoleMessages);
LOG.info("retriveJSError: OK");
return consoleMessages;//function() {return consoleMessages};
	};
	
Selenium.prototype.getSelectValues=function(selectLocator) {
	/**
	 * Gets all option values in the specified select drop-down
	 * 
	 * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down 
	 * @return string[] an array of all option values in the specified select drop-down
	 */
	var element = this.browserbot.findElement(selectLocator);
	
	var selectValues = [];
	
	for (var i = 0; i < element.options.length; i++) {
		var value = element.options[i].value;		
		selectValues.push(value);
	}
	
	return selectValues;
}
