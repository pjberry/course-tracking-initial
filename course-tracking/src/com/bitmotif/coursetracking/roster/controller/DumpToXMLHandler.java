package com.bitmotif.coursetracking.roster.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import com.bitmotif.coursetracking.roster.Roster;
import com.bitmotif.coursetracking.roster.database.RosterDAO;
import com.bitmotif.coursetracking.roster.xml.RosterXMLWriter;

public class DumpToXMLHandler {

	public void dump(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
		RosterDAO rosterDao = new RosterDAO();
		List<Roster> rosters = rosterDao.retrieveAll();

		Document document = new RosterXMLWriter().write(rosters);
		
		String mimetype = "application/xml";
		response.setContentType(mimetype);
		
		ServletOutputStream outputStream = response.getOutputStream();
		XMLOutputter outputter = new XMLOutputter();
	    outputter.output(document, outputStream);
	    outputStream.flush();
	    outputStream.close();   
	}

}
