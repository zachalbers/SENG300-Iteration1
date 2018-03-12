package mainFiles;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


import javax.swing.ListCellRenderer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;


public class TypeFinder {
	
	

	
	  int  referenceCount = 0;
	  int  declerationCount = 0;
	  boolean DEBUG = false;
	  String javaType = "";
	  String directory = "";
	  public String outputString;
	  


	  
	  public static void main(String[] args) {
		  

		  TypeFinder finder = new TypeFinder();
		  finder.run(args);




	  }
	  
	  public void run(String[] args) {
			if (args.length == 2 ) {
			  	directory = args[0];	
				javaType = args[1];	//Need to use to count which java type you want
				
				try {
					parseDirectory(directory);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Please provide directory and java type");
			}
		

		
		outputString = javaType + ". Declarations found: " + declerationCount + "; references found: " + referenceCount + ".";
		System.out.println(outputString);
	    
		//parseDirectory("/home/andrew/Projects/SENG300-Iteration1/TestFiles");


	  }
	  
	  


	  public void parse(String str) {
		  
		  Map options = JavaCore.getOptions();
		  options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);


		  
  
		  
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setCompilerOptions(options);
			parser.setSource(str.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			parser.setEnvironment(null, null, null, true);
			parser.setUnitName("doesThisMatter.java");
			

			
			
			 
			 
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
			cu.accept(new ASTVisitor() {
	 

				public boolean visit(TypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					
					if (javaType.equals(name)) declerationCount++;
					if (DEBUG) System.out.println("Declaration: " +name);
			
					
					
					if (node.getSuperclassType() != null) {
						if (javaType.equals(node.getSuperclassType().toString())) referenceCount++;
						if (DEBUG) System.out.println("This class extends " + node.getSuperclassType());
						
					}
					
					ITypeBinding nodeBinding = node.resolveBinding();
					if (nodeBinding.getInterfaces() != null) {
						ITypeBinding[] interfaces = nodeBinding.getInterfaces();
						for (ITypeBinding i : interfaces) {
							if (javaType.equals(i.getQualifiedName())) referenceCount++;
							if (DEBUG) System.out.println("implements Reference: " + i.getName());
							
						}
					}
					
					return super.visit(node); 
				}
				

				
				
				public boolean visit(VariableDeclarationFragment node) {
					
					String name = node.resolveBinding().getType().getName();
					if (javaType.equals(name)) referenceCount++;

					if (DEBUG) System.out.println("Variable Reference: " + name);


					return super.visit(node);
				}
				
				

				
				public boolean visit(MethodDeclaration node) {
				
					for (Object o : node.parameters()) {
						SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
						//System.out.println(javaType.equals(svd.getType().toString()));
						if (javaType.equals(svd.getType().toString())) referenceCount++;
					}
					
					return super.visit(node);
				}
				
				public boolean visit(MethodInvocation node) {
					
					
					return super.visit(node);
				}
				
		
				public boolean visit(ClassInstanceCreation node) {
					String name = node.getType().toString();
					if (javaType.equals(name)) referenceCount++;
					
					if (DEBUG) System.out.println("Reference: " + name);
	
					return false; // do not continue 
			}
				

				
				
				public boolean visit(AnnotationTypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					if (javaType.equals(name)) declerationCount++;
					
					if (DEBUG) System.out.println("Declaration: " + name);
					
					return false; // do not continue 
				}
				
				
				public boolean visit(EnumDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					if (javaType.equals(name)) declerationCount++;
					if (DEBUG) System.out.println("Declaration: " + name);
					
					ITypeBinding e = node.resolveBinding();
					if (e.getInterfaces() != null) {
						ITypeBinding[] interfaces = e.getInterfaces();
						for (ITypeBinding i : interfaces) {
							if (javaType.equals(i.getQualifiedName())) referenceCount++;
							if (DEBUG) System.out.println("Implements Reference: " + i.getName());
						}
					}
					

					
					return false; // do not continue 
				}
				


			});
	 
		}
	  
	  
	  
	  
	  
	  public String readFileToString(String filePath) throws IOException {
			StringBuilder fileData = new StringBuilder(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
	 
			char[] buf = new char[10];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				//System.out.println(numRead);
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
	 
			reader.close();
	 
			return  fileData.toString(); 
	 
	}
	  
	  public void parseDirectory(String filePath) throws IOException {

			  File directory = new File(filePath);
			  File[] files = null;
			  
			  try {
				  files = directory.listFiles(); 
				  if (files == null) throw new NullPointerException();
			  } catch (NullPointerException e) {
				  System.out.println("Directory '" + directory +"' does not exist.");
				  if (DEBUG) e.printStackTrace();
				  System.exit(0);  
			  }

			  for (File i: files) {
				  String currentFilePath = i.getAbsolutePath();
				  if (i.isFile()) parse(readFileToString(currentFilePath));
			  	}

		  
	  }
	  
	  
}
