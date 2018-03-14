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
	  boolean containsPackage = false;
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
				if (javaType.contains(".")) containsPackage = true;
				
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
					
					ITypeBinding nodeBinding = node.resolveBinding();
					if (containsPackage) {
						if (nodeBinding.getPackage() != null) {
							name = nodeBinding.getPackage().getName() + "." + name;
						}
					}
						
					if (javaType.equals(name)) declerationCount++;
					if (DEBUG) System.out.println("Declaration: " +name);
			

					if (node.getSuperclassType() != null) {
						
						if (containsPackage) {
							ITypeBinding superNodeBinding = node.getSuperclassType().resolveBinding();
							if (superNodeBinding.getPackage() != null) {
								String superClassName = superNodeBinding.getPackage().getName() + "." + node.getSuperclassType();
								if (javaType.equals(superClassName)) referenceCount++;
							}
						} else {
							if (javaType.equals(node.getSuperclassType().toString())) referenceCount++;
						}
						if (DEBUG) System.out.println("This class extends " + node.getSuperclassType());
						
					}

		
					if (nodeBinding.getInterfaces() != null) {
						ITypeBinding[] interfaces = nodeBinding.getInterfaces();
						if (containsPackage) {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getQualifiedName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getQualifiedName());
							}
						} else {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getName());
							}
						}
					}
					
					
					return super.visit(node); 
				}
				

				
				
				public boolean visit(VariableDeclarationFragment node) {
					String name;
					if (containsPackage) {
						name = node.resolveBinding().getType().getQualifiedName();
					} else {
						name = node.resolveBinding().getType().getName();
					}
			
					if (javaType.equals(name)) referenceCount++;
					if (DEBUG) System.out.println("Variable Reference: " + name);
	
					return super.visit(node);
				}
				
				

				
				public boolean visit(MethodDeclaration node) {
					String name;
					
//					if (node.isConstructor()) {
//						if (javaType.equals(node.getName().getFullyQualifiedName())) referenceCount++;
//						if (DEBUG) System.out.println("Reference: " + node.getName().getFullyQualifiedName());
//					}
					IMethodBinding imb = node.resolveBinding();
					if (containsPackage) {
						name = imb.getReturnType().getQualifiedName();
						if (javaType.equals(name)) referenceCount ++;
						if (DEBUG) System.out.println("Method Return Type Reference: " + name);
					}
					else {
						name = imb.getReturnType().toString();
						if (javaType.equals(name)) referenceCount ++;
						if (DEBUG) System.out.println("Method Return Type Reference: " + name);
					}
				
					for (Object o : node.parameters()) {
						SingleVariableDeclaration svd = (SingleVariableDeclaration) o;	
						if (containsPackage) {
							IVariableBinding nodeBinding = svd.resolveBinding();
							name = nodeBinding.getType().getQualifiedName();
							if (javaType.equals(name)) referenceCount++;
							if (DEBUG) System.out.println("Parameter Variable Reference: " + name);
							
						} else {
							name = svd.getType().toString();
							if (javaType.equals(name)) referenceCount++;
							if (DEBUG) System.out.println("Parameter Variable Reference: " + name);						
						}
					}
					
					return super.visit(node);
				}
				
				public boolean visit(MethodInvocation node) {

					
					return super.visit(node);
				}
				
		
				public boolean visit(ClassInstanceCreation node) {
					String name;
				
					if (containsPackage) {
						name = node.resolveTypeBinding().getQualifiedName();			
					} else {
						name = node.getType().toString();
					}
					
					if (javaType.equals(name)) referenceCount++;
					if (DEBUG) System.out.println("Instance Variable Reference: " + name);
					

					return false; // do not continue 
			}
				

				
				public boolean visit(AnnotationTypeDeclaration node) {
					String name;
					
					if (containsPackage) {
						name = node.resolveBinding().getQualifiedName();		
					} else {
						name = node.getName().getFullyQualifiedName();
					}
					
					if (javaType.equals(name)) declerationCount++;
					
					if (DEBUG) System.out.println("Declaration: " + name);
					
					return false; // do not continue 
				}
				
				
				public boolean visit(EnumDeclaration node) {
					String name;
					if (containsPackage) {
						name = node.resolveBinding().getQualifiedName();		
					} else {
						name = node.getName().getFullyQualifiedName();
					}
					
					if (javaType.equals(name)) declerationCount++;
					if (DEBUG) System.out.println("Declaration: " + name);

					
					ITypeBinding nodeBinding = node.resolveBinding();
					if (nodeBinding.getInterfaces() != null) {
						ITypeBinding[] interfaces = nodeBinding.getInterfaces();
						if (containsPackage) {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getQualifiedName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getQualifiedName());
							}
						} else {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getName());
							}
						}
					}

					return false; // do not continue 
				}
				
				



			});
	 
		}
	  
	  
	  
	  
	  
	  public String readFile(String filePath) throws IOException {
			StringBuilder fileData = new StringBuilder(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			int numRead = 0; 
			char[] buffer = new char[10];

			while ((numRead = reader.read(buffer)) != -1) {
				String readData = String.valueOf(buffer, 0, numRead);
				fileData.append(readData);
				buffer = new char[1024];
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
				  if (i.isFile()) parse(readFile(currentFilePath));
			  	}

		  
	  }
	  
	  
}
