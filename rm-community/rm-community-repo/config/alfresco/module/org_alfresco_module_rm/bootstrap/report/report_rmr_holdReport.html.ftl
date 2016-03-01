<#--
 #%L
 This file is part of Alfresco.
 %%
 Copyright (C) 2005 - 2016 Alfresco Software Limited
 %%
 Alfresco is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 Alfresco is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
  
 You should have received a copy of the GNU Lesser General Public License
 along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 #L%
-->
<html>
   <head>
      <style type="text/css"><!--
      body
      {
         font-family: Arial, sans-serif;
         font-size: 14px;
         color: #4c4c4c;
      }

      a, a:visited
      {
         color: #0072cf;
      }
      --></style>
   </head>
   <body bgcolor="#dddddd">
      <table width="100%" cellpadding="20" cellspacing="0" border="0" bgcolor="#dddddd">
         <tr>
            <td width="100%" align="center">
               <table width="70%" cellpadding="0" cellspacing="0" bgcolor="white" style="background-color: white; border: 1px solid #aaaaaa;">
                  <tr>
                     <td width="100%">
                        <table width="100%" cellpadding="0" cellspacing="0" border="0">
                           <tr>
                              <td style="padding: 10px 30px 0px;">
                                 <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                    <tr>
                                       <td>
                                          <table cellpadding="0" cellspacing="0" border="0">
                                             <tr>
                                                <td>
                                                   <img src="${shareUrl}/res/components/images/task-64.png" alt="" width="64" height="64" border="0" style="padding-right: 20px;" />
                                                </td>
                                                <td>
                                                   <div style="font-size: 22px; padding-bottom: 4px;">
                                                      ${message("file.report.hold.report")}
                                                   </div>
                                                </td>
                                             </tr>
                                          </table>
                                          <div style="font-size: 14px; margin: 12px 0px 24px 0px; padding-top: 10px; border-top: 1px solid #aaaaaa;">
                                          <table cellpadding="2" cellspacing="3" border="0">
                                             <tr>
                                                <td><i>${message("file.report.hold.name")}:</i></td>
                                                <td>${node.properties["cm:name"]}</td>
                                             </tr>
                                             <tr>
                                                <td><i>${message("file.report.hold.description")}:</i></td>
                                                <td>
                                                   <#if node.properties["cm:description"]??>
                                                   ${node.properties["cm:description"]}
                                                   </#if>
                                                </td>
                                             </tr>
                                             <tr>
                                                <td><i>${message("file.report.hold.reason")}:</i></td>
                                                <td>${node.properties["rma:holdReason"]}</td>
                                             </tr>
                                             <tr>
                                                <td><i>${message("file.report.createdby")}:</i></td>
                                                <td>${reportUser}</td>
                                             </tr>
                                             <tr>
                                                <td><i>${message("file.report.createdon")}:</i></td>
                                                <td>${reportDate}</td>
                                             </tr>
                                          </table>
                                          <#if  node.childAssociations["rma:frozenRecords"]??>
                                             <div style="font-size: 14px; margin: 12px 0px 24px 0px; padding-top: 10px; border-top: 1px solid #aaaaaa;">
                                             <table cellpadding="2" cellspacing="3" border="0">
                                                <tr>
                                                   <td><i>${message("file.report.hold.held")}:</i></td>
                                                   <td></td>
                                                </tr>
                                             </table>
                                             <table cellpadding="0" callspacing="0" border="0" bgcolor="#eeeeee" style="padding:10px; border: 1px solid #aaaaaa;">
                                                <tr>
                                                   <td>
                                                      <table cellpadding="0" cellspacing="0" border="0">
                                                         <#list node.childAssociations["rma:frozenRecords"] as child>
                                                            <tr>
                                                               <td valign="top">
                                                                  <img src="${url}/${child.icon32}" alt="" width="32" height="32" border="0" style="padding-right: 10px;" />
                                                               </td>
                                                               <td>
                                                                  <table cellpadding="2" cellspacing="0" border="0">
                                                                     <tr>
                                                                        <td>${child.properties["rma:identifier"]} <b>${child.properties.name}</b></td>
                                                                     </tr>
                                                                  </table>
                                                               </td>
                                                            </tr>
                                                         </#list>
                                                      </table>
                                                   </td>
                                                </tr>
                                             </table>
                                          </#if>
                                          <div style="font-size: 14px; margin: 12px 0px 24px 0px; padding-top: 10px; border-top: 1px solid #aaaaaa;">
                                          </div>
                                       </td>
                                    </tr>
                                 </table>
                              </td>
                           </tr>
                           <tr>
                              <td style="padding: 10px 30px;">
                                 <img src="${shareUrl}/themes/default/images/app-logo.png" alt="" width="117" height="48" border="0" />
                              </td>
                           </tr>
                        </table>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
      </table>
   </body>
</html>