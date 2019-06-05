(ns Maze)
;;importing string method from java file
(require '[clojure.string :as s])

;converting the text file into map
(defn convert []
  ;opening the file and reading the input file line by line
  (with-open [rdr (clojure.java.io/reader "map.txt")]
    (reduce conj [] (line-seq rdr)))
  )

;opening the file for finding the number of rows in an input map
(with-open [rdr (clojure.java.io/reader "map.txt")]
  ;saving the number of rows into a variable co
  (def co(count (line-seq rdr))) )

;;Printing the given input file


;;defining a global validate for last check whether a path is found or not for that the validate is used
(def validate 1)
;
(def q (vec(convert)))
(def convertomap
  (mapv #(s/split % #"") q))

(def maze2DArray convertomap)
;(def c )
;(println c)



;(println (get-in maze2DArray [7 12]) )
(def c (count (get maze2DArray 0 0)))
;setting the column Length  dynamically
(def colLen c)
;setting the indexRow Length dynamically
(def rowLen co)

(def check false)
;Printing the first content in the console such as input Map
(println "Input Map")
(dorun (map println maze2DArray))
(println "Output :")
;(def z (assoc-in z [0 0] "*" ))
;(assoc [z] [0 0] "l")
;(print ((aget getMapValues 0 0 "#")))
;(println maze2DArray )

;;function to find the path
(defn mazehunt [indexRow indexColumn]
  ;finding the right position and checking the up position is goal or not
  (if (and (< (+ indexColumn 1) colLen ) (= (get-in maze2DArray [indexRow  (+ indexColumn 1) ]) "@"))
    (do
      ; it is going to print + in the @ position
      (def maze2DArray (assoc-in maze2DArray [indexRow indexColumn] "+"))

      ;(print maze2DArray)
      ;defining the global validate to 2 as it found the treasure
      (def validate 2)
      (println "Found the path")
      ;printing the path in vector line by line
      (dorun (map println maze2DArray))
      ;used to get out of the final loop
      true
      ))
  ;checking the down position and whether the goal is reached or not
  (if (and (< (+ indexRow 1) rowLen ) (= (get-in maze2DArray [(+ indexRow 1)  indexColumn ]) "@"))
    (do
      ;going to print + in the @ position
      (def maze2DArray (assoc-in maze2DArray [indexRow indexColumn] "+"))

      ;(print maze2DArray)
      ;defining the global validate to 2 as it found the treasure
      (def validate 2)
      ;printing the path in vector line by line
      (dorun (map println maze2DArray))
      ;getting out of the loop
      true
      ))
  (def z (- indexColumn 1))
  ;checking the left position is treasure or not and the first check in if is to check whether it is going out of Index in array
  (if (and (>= (- indexColumn 1) 0 ) (= (get-in maze2DArray [indexRow  (- indexColumn 1) ]) "@"))
    (do
      ;going to print + in the previous position
      (def maze2DArray (assoc-in maze2DArray [indexRow indexColumn] "+"))
      ;defining the global validate
      (def validate 2)
      ;printing the output map line by line
      (dorun (map println maze2DArray))
      ;getting out of the loop
      true
      ))
  ;checking the up position is treasure or not and in the first check if it is hitting the boundary condition or not
  (if (and(>= (- indexRow 1) 0) (= (get-in maze2DArray [(- indexRow 1) indexColumn]) "@"))
    (do
      ;going to print + in the previous position
      (def maze2DArray (assoc-in maze2DArray [indexRow indexColumn] "+"))
      ;printing the output map line by line
      (dorun (map println maze2DArray))
      ;defining the global validate
      (def validate 2)
      ;getting out of the loop
      true
      ))
  ;declaring variable inside the check function for recursion
  (def check false)
  ;marking the current row and column as +
  (def maze2DArray (assoc-in maze2DArray [indexRow indexColumn] "+"))
  ;checking the up if it is "- " call the recursion by incresing the row with one value
  (if (and (>= (- indexRow 1) 0) (= (get-in maze2DArray [(- indexRow 1) indexColumn]) "-"))
    ;calling the recursion
    (mazehunt (- indexRow 1)  indexColumn))
  ;checking the right if it is "- " call the recursion by incresing the column with one value
  (if (and (< (+ indexColumn 1) colLen ) (= (get-in maze2DArray [indexRow  (+ indexColumn 1) ]) "-"))
    ;calling the recursion
    (mazehunt indexRow (+ indexColumn 1)))
  ;checking the down if it is "- " call the recursion by incresing the row with one value
  (if (and (< (+ indexRow 1) rowLen ) (= (get-in maze2DArray [(+ indexRow 1)  indexColumn ]) "-"))
    ;calling the recursion
    (mazehunt (+ indexRow 1)  indexColumn))
  ;checking the left if it is "- " call the recursion by incresing the column with one value
  (if(and (>= (- indexColumn 1) 0 ) (= (get-in maze2DArray [indexRow  (- indexColumn 1) ]) "-"))
    ;calling the recursion
    (mazehunt indexRow (- indexColumn 1)))

  (if (= check false)
    ;printing ! where there is no path
    (def maze2DArray (assoc-in maze2DArray [indexRow indexColumn] "!")))


  )
;calling mazehunt function for finding the path
(mazehunt 0 0)
;checking wether there is a path exist or not
(if (= validate 1)
  (do (println "Cannot find the path")
      (def input_map (slurp "map.txt"))
      ;printing the map if there is no path
      (dorun (map println maze2DArray))))
