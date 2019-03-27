# Related information for testing

## Data Model:
- opalData.trade (Create a new DDG at TraDE Middleware and upload the file)
- namespace: http://de.uni-stuttgart.iaas/opalChor
- name: OpalSimulationChoreography

## Preparation
- Create a new DDG resource
- Upload the 'opalData.trade' file to the created DDG resource (POST .../serialized-graph)
- Instantiate the 'simInput' data object
- Resolve the UUIDs of the 'lattice' and 'opal_in' data element instances
  lattice ID: db297d2a-bf73-4739-add5-4744f5a1e18c
  opal_in ID: 24744c3b-43b7-462c-949e-1d802be90f80
- Create and associate a new data value to each of the data element instances
  - lattice
    - example request: 
      {
        "name": "lattice",
        "createdBy": "hahnml",
        "type": "binary",
        "contentType": "text/plain"
      }
    - lattice data value ID: 6caa4d67-a896-4068-b16c-6e0177b7be9b
  - opal_in
    - example request: 
      {
        "name": "opal_in",
        "createdBy": "hahnml",
        "type": "binary",
        "contentType": "text/plain"
      }
      - opal_in data value ID: 94907211-953e-4acb-ad0a-2009c4a25572
- Upload the binary data to both data values (POST /dataValues/{dataValueId}/data)
  - lattice: opalbcc.dat
  - opal_in: opal.in
- Try to run the simulation (send example request to opal wrapper service API)

## Example Request:
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:opal="http://www.uni-stuttgart.de/iaas/serviceWrapper/opalMC/">
   <soapenv:Header/>
   <soapenv:Body>
      <opal:runOpalMC>
         <simulationIdentifier>
            <key>simulationID</key>
            <value>testRun</value>
         </simulationIdentifier>
         <dataModelRef>
            <namespaceURI>http://de.uni-stuttgart.iaas/opalChor</namespaceURI>
            <localName>OpalSimulationChoreography</localName>
         </dataModelRef>
         <opalInput>
            <dataObjectName>simInput</dataObjectName>
            <dataElementName>opal_in</dataElementName>
         </opalInput>
         <opalBccLattice>
            <dataObjectName>simInput</dataObjectName>
            <dataElementName>lattice</dataElementName>
         </opalBccLattice>
         <numberOfSnapshots>10</numberOfSnapshots>
         <resultSnapshots isCollectionDataElement="true">
            <dataObjectName>simResults</dataObjectName>
            <dataElementName>snapshots[]</dataElementName>
         </resultSnapshots>
         <resultSaturation>
            <dataObjectName>simResults</dataObjectName>
            <dataElementName>saturation</dataElementName>
         </resultSaturation>
         <resultReport>
            <dataObjectName>simResults</dataObjectName>
            <dataElementName>report</dataElementName>
         </resultReport>
      </opal:runOpalMC>
   </soapenv:Body>
</soapenv:Envelope>
```

