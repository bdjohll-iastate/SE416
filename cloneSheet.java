public XSSFSheet cloneSheet(int sheetNum, String newName) {
    // attempt to clone sheet
    validateSheetIndex(sheetNum);
    XSSFSheet srcSheet = sheets.get(sheetNum);
    newName = checkName(newName);
    XSSFSheet clonedSheet = createSheet(newName);


    // copy sheet's relations
    List<RelationPart> rels = srcSheet.getRelationParts();
    XSSFDrawing dg = copyRelations(rels, clonedSheet);
    cloneExternRelations(srcSheet, clonedSheet);

    // copy sheet's contents
    readByteArray(srcSheet, clonedSheet);

    CTWorksheet ct = clonedSheet.getCTWorksheet();
    //warn for unsupported CTWorksheet features
    warnCTWorksheet(ct);

    clonedSheet.setSelected(false);

    //handle drawings
    cloneDrawings(dg, ct, srcSheet, clonedSheet);

    return clonedSheet;
}

//If name is empty generate one, otherwise validate given name
private string checkName(String newName, XSSFSheet srcSheet){
    if (newName == null) {
        String srcName = srcSheet.getSheetName();
        return getUniqueSheetName(srcName);
    } else {
        validateSheetName(newName);
        return newName;
    }
}

//copy relations in cloned sheet, returning drawing if it exists
private XSSFDrawing copyRelations(List<RelationPart> rels, XSSFSheet sheet){
    XSSFDrawing dg = null;
    for(RelationPart rp : rels) {
        POIXMLDocumentPart r = rp.getDocumentPart();
        // do not copy the drawing relationship, it will be re-created
        if(r instanceof XSSFDrawing) {
            dg = (XSSFDrawing)r;
            continue;
        }

        addRelation(rp, clonedSheet);
    }
    return dg
} 

//Attempt to copy external relationships to cloned sheet
private void cloneExternRelations(XSSFSheet srcSheet, XSSFSheet clonedSheet){
    try {
        for(PackageRelationship pr : srcSheet.getPackagePart().getRelationships()) {
            if (pr.getTargetMode() == TargetMode.EXTERNAL) {
                clonedSheet.getPackagePart().addExternalRelationship
                        (pr.getTargetURI().toASCIIString(), pr.getRelationshipType(), pr.getId());
            }
        }
    } catch (InvalidFormatException e) {
        throw new POIXMLException("Failed to clone sheet", e);
    }
}

//Clone byte array from source sheet to cloned sheet
private void readByteArray(XSSFSheet srcSheet, XSSFSheet clonedSheet){
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        srcSheet.write(out);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray())) {
            clonedSheet.read(bis);
        }
    } catch (IOException e){
        throw new POIXMLException("Failed to clone sheet", e);
    }
}

private void warnCTWorksheet(CTWorksheet ct){
    if(ct.isSetLegacyDrawing()) {
        logger.log(POILogger.WARN, "Cloning sheets with comments is not yet supported.");
        ct.unsetLegacyDrawing();
    }
    if (ct.isSetPageSetup()) {
        logger.log(POILogger.WARN, "Cloning sheets with page setup is not yet supported.");
        ct.unsetPageSetup();
    }
}

private void cloneDrawings(XSSFDrawing dg, CTWorksheet ct, XSSFSheet srcSheet, XSSFSheet clonedSheet){
    // clone the sheet drawing along with its relationships
    if (dg != null) {
        if(ct.isSetDrawing()) {
            // unset the existing reference to the drawing,
            // so that subsequent call of clonedSheet.createDrawingPatriarch() will create a new one
            ct.unsetDrawing();
        }
        XSSFDrawing clonedDg = clonedSheet.createDrawingPatriarch();
        // copy drawing contents
        clonedDg.getCTDrawing().set(dg.getCTDrawing());

        clonedDg = clonedSheet.createDrawingPatriarch();

        // Clone drawing relations
        List<RelationPart> srcRels = srcSheet.createDrawingPatriarch().getRelationParts();
        for (RelationPart rp : srcRels) {
            addRelation(rp, clonedDg);
        }
    }
}