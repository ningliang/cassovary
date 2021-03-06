/*
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.twitter.cassovary.graph

import collection.mutable.ArrayBuffer

/**
 * Represents a directed path of nodes in a graph. No loop detection is done.
 */
case class DirectedPath[T](val nodes: Seq[T]) {

  /**
   * @return the number of nodes on this path
   */
  def length = nodes.length

  /**
   * Check if this path contains a given node.
   * @param node the node to check membership of in this path
   * @return <code>true</code> if the node is in this path
   */
  def exists(node: Node) = nodes.contains(node)

}

object DirectedPath {
  trait Builder[T] {
    /**
     * Appends a node to the end of this path.
     * @param node the node to append
     * @return this Builder for chaining
     */
    def append(node: T): this.type

    /**
     * Takes the snapshot of the path currently being built to return an immutable DirectedPath.
     */
    def snapshot: DirectedPath[T]

    /**
     * Clear this path
     */
    def clear()
  }

  def builder[T](): Builder[T] = {
    new Builder[T]() {
      private val path = new ArrayBuffer[T]

      def append(node: T) = {
        path.append(node)
        this
      }

      def snapshot = DirectedPath(path.toList)

      def clear() { path.clear() }
    }
  }

}
